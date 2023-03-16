package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdTag;
import com.powernode.model.Result;
import com.powernode.service.ProdTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "标签管理接口")
@RequestMapping("/prod/prodTag")
public class ProdTagController {
    @Autowired
    private ProdTagService prodTagService;

    @GetMapping("/page")
    @ApiOperation("根据名称和状态查询标签分页")
    public Result<Page<ProdTag>> loadProdTagPage(Page<ProdTag> prodTagPage, ProdTag prodTag) {
        return Result.success(prodTagService.loadProdTagPage(prodTagPage, prodTag));
    }

    @GetMapping("/listTagList")
    @ApiOperation("加载所有的标签列表")
    public Result<List<ProdTag>> listProdTagList() {
        return Result.success(prodTagService.list());
    }
}
