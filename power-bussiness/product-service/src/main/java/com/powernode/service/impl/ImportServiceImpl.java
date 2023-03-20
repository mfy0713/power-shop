package com.powernode.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.config.EsConfig;
import com.powernode.constant.EsConstant;
import com.powernode.dao.ProdESDao;
import com.powernode.domain.Prod;
import com.powernode.model.ProdES;
import com.powernode.service.ImportService;
import com.powernode.service.ProdService;
import com.powernode.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ImportServiceImpl implements ImportService, CommandLineRunner {
    @Autowired
    private ProdService prodService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;    //高级es客户端对象
    @Autowired
    private EsConfig esConfig;

    //增量导入开始时间
    private LocalDateTime t1;

    @Override
    public void importAll() {
        //查询索引是否存在
        CountRequest countRequest = new CountRequest(EsConstant.ES_INDEX_NAME);
        CountResponse countResponse = null;
        try {
            countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (countResponse != null && countResponse.getCount() > 0) {
            //索引已经存在，无需导入
            log.info("索引已经存在，不需要导入");
            t1 = LocalDateTime.now();
            return;
        }
        log.info("全量导入开始---" + LocalDateTime.now());
        long start = System.currentTimeMillis();
        //获取每页大小
        Integer size = esConfig.getSize();
        //获取总页数
        Integer allCount = prodService.selectCount(null, null);
        Integer pageCount = allCount % size == 0 ? allCount / size : (allCount / size) + 1;
        //循环导入
        //多线程计数器--计数器取线程数量
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);

        for (int i = 1; i <= pageCount; i++) {
            final int current = i;
            //优化导入，使用多线程执行导入
            ThreadPoolUtil.poolExecutor.submit(() -> {
                //导入es
                log.info("--第{}页数据开始导入--", current);
                importEs(current, size, null, null);
                //线程计数器--
                countDownLatch.countDown();
            });


        }
        //强制唤醒主线程--超过10分钟，线程计数器还没有清零，则不再等待
        try {
            countDownLatch.await(10, TimeUnit.MINUTES);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("全量导入结束---,使用时间=" + (end - start));
        //开始计时
        t1 = LocalDateTime.now();
    }

    @Override   //初始延迟2分钟开始计时，每隔2分钟执行一次
//    @Scheduled(fixedRate = 120 * 1000, initialDelay = 120 * 1000)
    public void importUpdate() {
        log.info("增量导入开始---" + new Date());
        //结束时间
        LocalDateTime t2 = LocalDateTime.now();
        Integer count = prodService.selectCount(t1, t2);
        if (t1 == null || count <= 0) {
            //没有发生变化的数据，不需要执行导入，开始时间重新计时
            t1 = t2;
            return;
        }
//获取每页大小
        Integer size = esConfig.getSize();
        //获取总页数
        Integer allCount = prodService.selectCount(t1, t2);
        Integer pageCount = count % size == 0 ? count / size : (count / size) + 1;
        //循环导入
        //多线程计数器--计数器取线程数量
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);

        for (int i = 1; i <= pageCount; i++) {
            final int current = i;
            //优化导入，使用多线程执行导入
            ThreadPoolUtil.poolExecutor.submit(() -> {
                //导入es
                log.info("--第{}页数据开始导入--", current);
                importEs(current, size, t1, t2);
                //线程计数器--
                countDownLatch.countDown();
            });


        }
        //强制唤醒主线程--超过10分钟，线程计数器还没有清零，则不再等待
        try {
            countDownLatch.await(10, TimeUnit.MINUTES);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //开始计时
        t1 = LocalDateTime.now();
        System.out.println("增量导入执行完成，t1=" + t1);
    }

    //将数据库数据导入es
    private void importEs(Integer pageNo, Integer size, LocalDateTime t1, LocalDateTime t2) {
        //分页的对象
        Page<Prod> page = new Page<>(pageNo, size);
        //查询分页的数据
        List<ProdES> prodESList = prodService.loadProdToProdES(page, t1, t2);
        //批量请求ES的对象
        BulkRequest bulkRequest = new BulkRequest();
        //循环prodEs
        prodESList.forEach(prodES -> {
            IndexRequest indexRequest = new IndexRequest(EsConstant.ES_INDEX_NAME)  //索引名称
                    .id(prodES.getProdId().toString()); //主键
            //添加请求到批量请求对象
            indexRequest.source(JSON.toJSONString(prodES), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        //批量导入es
        try {
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        importAll();
    }
}
