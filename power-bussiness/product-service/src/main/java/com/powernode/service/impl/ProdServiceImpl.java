package com.powernode.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.model.ProdCommStatics;
import com.powernode.domain.ProdTagReference;
import com.powernode.domain.Sku;
import com.powernode.dto.ProdDto;
import com.powernode.mapper.ProdCommMapper;
import com.powernode.mapper.ProdTagReferenceMapper;
import com.powernode.mapper.SkuMapper;
import com.powernode.model.ProdES;
import com.powernode.service.ProdTagReferenceService;
import com.powernode.service.SkuService;
import com.powernode.util.AuthUtil;
import com.powernode.vo.ProdInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.ProdMapper;
import com.powernode.domain.Prod;
import com.powernode.service.ProdService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService {

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @Autowired
    private ProdTagReferenceMapper prodTagReferenceMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ProdCommMapper prodCommMapper;

    @Override
    public Page<Prod> loadProdPage(Page<Prod> page, Prod prod) {
        return prodMapper.selectPage(page, new LambdaQueryWrapper<Prod>()
                .like(StringUtils.hasText(prod.getProdName()), Prod::getProdName, prod.getProdName())
                .eq(!ObjectUtils.isEmpty(prod.getStatus()), Prod::getStatus, prod.getStatus()));
    }

    @Override
    @Transactional
    public Integer addProd(ProdDto prodDto) {
        prodDto.setCreateTime(new Date());
        prodDto.setUpdateTime(new Date());
        prodDto.setVersion(1);
        prodDto.setShopId(AuthUtil.getShopId());
        if (prodDto.getStatus().equals(1)) {
            prodDto.setPutawayTime(new Date());
        }
        //配送模式--序列化为json
        prodDto.setDeliveryMode(JSON.toJSONString(prodDto.getDeliveryModeVo()));
        int count = prodMapper.insert(prodDto);
        if (count > 0) {
            //插入sku表
            List<Sku> skuList = prodDto.getSkuList();
            skuList.forEach(sku -> {
                sku.setProdId(prodDto.getProdId());
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                sku.setVersion(1);
            });
            //批量插入
            boolean flag = skuService.saveBatch(skuList);
            //插入prod_tag_perference
            List<ProdTagReference> tagReferences = new ArrayList<>();
            prodDto.getTagList().forEach(tagId -> {
                ProdTagReference reference = new ProdTagReference();
                reference.setTagId(tagId);
                reference.setProdId(prodDto.getProdId());
                reference.setShopId(AuthUtil.getShopId());
                reference.setStatus(true);
                //添加集合
                tagReferences.add(reference);
            });
            //批量插入refrence
            flag = prodTagReferenceService.saveBatch(tagReferences);

        }
        return count;
    }

    /**
     * 根据商品id返回商品信息
     *
     * @param prodId
     * @return
     */
    @Override
    @Transactional
    public ProdDto loadProdWithProdId(Long prodId) {
        //查询产品列表
        ProdDto prodDto = new ProdDto();
        Prod prod = prodMapper.selectById(prodId);
        prodDto.setBrief(prod.getBrief());
        prodDto.setCategoryId(prod.getCategoryId());
        prodDto.setContent(prod.getContent());
        prodDto.setCreateTime(prod.getCreateTime());
        prodDto.setImgs(prod.getImgs());
        prodDto.setOriPrice(prod.getOriPrice());
        prodDto.setPic(prod.getPic());
        prodDto.setPrice(prod.getPrice());
        prodDto.setProdId(prod.getProdId());
        prodDto.setProdName(prod.getProdName());
        prodDto.setPutawayTime(prod.getPutawayTime());
        prodDto.setShopId(prod.getShopId());
        prodDto.setSoldNum(prod.getSoldNum());
        prodDto.setStatus(prod.getStatus());
        prodDto.setTotalStocks(prod.getTotalStocks());
        prodDto.setUpdateTime(prod.getUpdateTime());
        prodDto.setVersion(prod.getVersion());
        prodDto.setDeliveryMode(prod.getDeliveryMode());


        List<Sku> skuList = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId));

        List<ProdTagReference> tagReferences = prodTagReferenceMapper.selectList(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodId));

        List<Long> prodTagReferenceList = tagReferences.stream().map(ProdTagReference::getTagId).collect(Collectors.toList());


        prodDto.setSkuList(skuList);

        prodDto.setTagList(prodTagReferenceList);

        return prodDto;
    }

    /**
     * 根据商品id删除商品
     *
     * @param prodId
     * @return
     */
    @Override
    public Integer deleteByPid(Long prodId) {
        int count = prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodId));

        count = skuMapper.delete(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId));

        count = prodMapper.deleteById(prodId);


        return count;
    }

    /**
     * 更新商品信息
     *
     * @param prodDto
     * @return
     */
    @Override
    @Transactional
    public Integer updateProd(ProdDto prodDto) {
        int count = prodMapper.updateById(prodDto);


        int skuv = 1;


//        List<Sku> skuList = new ArrayList<>();

//        List<Sku> skuList = prodDto.getSkuList();

        skuMapper.update(null, new LambdaUpdateWrapper<Sku>()
                .eq(Sku::getProdId, prodDto.getProdId())
                .set(Sku::getUpdateTime, new Date()));

//        List<Sku> skuList1 = new ArrayList<>();
//        skuList.forEach(sku -> {
//            sku.setProdId(prodDto.getProdId());
//            sku.setCreateTime(sku.getCreateTime());
//            sku.setUpdateTime(new Date());
//            sku.setVersion(sku.getVersion() + 1);
//
//
//        });

//        skuMapper.delete(new LambdaQueryWrapper<Sku>()
//                .eq(Sku::getProdId, prodDto.getProdId()));

        //批量插入
//        boolean flag = skuService.saveBatch(skuList);


        prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodDto.getProdId()));
        //插入prod_tag_perference
        List<ProdTagReference> tagReferences = new ArrayList<>();
        prodDto.getTagList().forEach(tagId -> {
            ProdTagReference reference = new ProdTagReference();
            reference.setTagId(tagId);
            reference.setProdId(prodDto.getProdId());
            reference.setShopId(AuthUtil.getShopId());
            reference.setStatus(true);
            //添加集合
            tagReferences.add(reference);
        });
        //批量插入refrence
        boolean flag = prodTagReferenceService.saveBatch(tagReferences);


        return count;
    }

    /**
     * 分页查询满足日期范围的商品数据
     *
     * @param page
     * @param d1
     * @param d2
     * @return
     */
    @Override
    public List<ProdES> loadProdToProdES(Page<Prod> page, LocalDateTime d1, LocalDateTime d2) {
        Page<Prod> prodPage = prodMapper.selectPage(page, new LambdaQueryWrapper<Prod>()
                .eq(Prod::getStatus, 1)
                .between(d1 != null && d2 != null, Prod::getUpdateTime, d1, d2));
        //获取商品集合
        List<Prod> prodList = prodPage.getRecords();
        if (CollectionUtils.isEmpty(prodList)) {
            return Collections.emptyList();
        }
        //讲集合中的Prod转换为ProdES
        List<ProdES> prodESList = new ArrayList<>();
        //获取商品的id集合
        List<Long> prodIds = prodList.stream()
                .map(Prod::getProdId)
                .collect(Collectors.toList());
        //获取prodIds对应的tag集合
        List<ProdTagReference> tagReferenceList = prodTagReferenceService.list(
                new LambdaQueryWrapper<ProdTagReference>()
                        .in(ProdTagReference::getProdId, prodIds)
        );
        //获取商品评论统计
        List<ProdCommStatics> prodCommStatics = prodCommMapper.selectProdCommStatics(prodIds);
        //循环每一个prod转换为ProdES
        prodList.forEach(prod -> {
            ProdES prodES = new ProdES();
            //将prod属性付给prodEs
            BeanUtils.copyProperties(prod, prodES);
            //处理标签
            List<Long> tagList = tagReferenceList.stream()
                    .filter(prodTagReference -> prodTagReference.getProdId().equals(prod.getProdId()))
                    .map(ProdTagReference::getTagId)
                    .collect(Collectors.toList());
            //赋值给当前对象
            prodES.setTagList(tagList);
            //处理评论
            List<ProdCommStatics> staticsList = prodCommStatics.stream()
                    .filter(prodCommStatics1 -> prodCommStatics1.getProdId().equals(prod.getProdId()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(staticsList)) {
                ProdCommStatics prodCommStatics1 = staticsList.get(0);
                //设置评论数
                prodES.setPraiseNumber(prodCommStatics1.getAllCount());
                Integer goodCount = prodCommStatics1.getGoodCount();
                Integer allCount = prodCommStatics1.getAllCount();
                //好评率
                if (!prodCommStatics1.getGoodCount().equals(0)) {
                    BigDecimal goodLv = new BigDecimal(goodCount).divide(new BigDecimal(allCount), 2, RoundingMode.HALF_UP);
                    prodES.setPositiveRating(goodLv.multiply(new BigDecimal(100)));
                }
            }
            //添加prodES到集合
            prodESList.add(prodES);
        });

        return prodESList;
    }

    /**
     * 满足日期范围的商品记录数据
     *
     * @param d1
     * @param d2
     * @return
     */
    @Override
    public Integer selectCount(LocalDateTime d1, LocalDateTime d2) {
        return prodMapper.selectCount(new LambdaQueryWrapper<Prod>()
                .eq(Prod::getStatus, 1)
                .between(d1 != null && d2 != null, Prod::getUpdateTime, d1, d2));
    }

    @Override
    public ProdInfoVo getProdInfoById(Long prodId) {
        ProdInfoVo prodInfoVo = new ProdInfoVo();
        Prod prod = prodMapper.selectById(prodId);
        if (ObjectUtils.isEmpty(prod)) {
            throw new IllegalArgumentException("商品不存在");
        }
        //查询当前商品sku集合
        List<Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId)
                .eq(Sku::getStatus, 1));
        //赋值给当前vo对象
        BeanUtils.copyProperties(prod, prodInfoVo);
        prodInfoVo.setSkuList(skuList);
        return prodInfoVo;
    }
}
