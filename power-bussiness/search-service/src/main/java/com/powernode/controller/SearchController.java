package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.model.ProdES;
import com.powernode.model.Result;
import com.powernode.service.SearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "搜索服务API")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/prod/prodListByTagId")
    public Result<Page<ProdES>> searchProdByTagId(Integer tagId, Integer size,
                                                  @RequestParam(name = "current", required = false, defaultValue = "1")
                                                          Integer current) {
        return Result.success(searchService.findByTagId(tagId, current, size));
    }

    @GetMapping("/search/searchProdPage")
    public Result<Page<ProdES>> findProdByName(String prodName, Integer current, Integer size, Integer sort) {
        return Result.success(searchService.findByProdName(prodName, current, size, sort));
    }
}
