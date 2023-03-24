package com.powernode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.model.ProdES;
import com.powernode.service.SearchService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public Page<ProdES> findByTagId(Integer tagId, Integer current, Integer size) {
        Page<ProdES> prodESPage = new Page<>(current, size);
        //匹配tagid
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("tagList", tagId);
        //构建查询条件对象
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQueryBuilder)
                .withPageable(PageRequest.of(current - 1, size))   //第一页从0开始
                .build();
        SearchHits<ProdES> searchHits = elasticsearchTemplate.search(nativeSearchQuery, ProdES.class);
        //获取查询结果集合
        List<ProdES> prodESList = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        prodESPage.setRecords(prodESList);
        prodESPage.setTotal(prodESPage.getTotal());

        return prodESPage;
    }

    @Override
    public Page<ProdES> findByProdName(String prodName, Integer current, Integer size, Integer sort) {
        Page<ProdES> prodESPage = new Page<>(current, size);
        //根据商品名字模糊搜索
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("prodName", prodName);
        //高亮字段
        HighlightBuilder.Field field = new HighlightBuilder.Field("prodName");
        //设置前后缀
        field.preTags("<i style='color:red'>");
        field.postTags("</i>");
        //构造查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQueryBuilder)
                .withPageable(PageRequest.of(current - 1, size))
                .withHighlightFields(field)
                .withSorts(mySort(sort))
                .build();
        //执行查询
        SearchHits<ProdES> searchHits = elasticsearchTemplate.search(nativeSearchQuery, ProdES.class);
        //处理高亮字段
        searchHits.forEach(searchHit -> {
            ProdES prodES = searchHit.getContent();
            //获取高亮字段
            List<String> list = searchHit.getHighlightField("prodName");
            //获取高亮的商品名字
            String name = list.get(0);
            prodES.setProdName(name);
        });
        List<ProdES> prodESList = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        //设置分页数据
        prodESPage.setRecords(prodESList);
        prodESPage.setTotal(searchHits.getTotalHits());

        return prodESPage;
    }

    //排序对象
    private SortBuilder mySort(Integer sort) {
        switch (sort) {
            case 0: //综合
                return SortBuilders.fieldSort("positiveRating").order(SortOrder.DESC);
            case 1: //销量
                return SortBuilders.fieldSort("soldNum").order(SortOrder.DESC);
            case 2:
                return SortBuilders.fieldSort("price").order(SortOrder.ASC);
            default:
                throw new IllegalArgumentException("排序条件不正确");
        }
    }
}
