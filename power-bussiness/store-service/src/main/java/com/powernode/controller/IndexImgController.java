package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.IndexImg;
import com.powernode.model.Result;
import com.powernode.service.IndexImgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "轮播图API")
@RequestMapping("/admin/indexImg")
public class IndexImgController {
    @Autowired
    private IndexImgService indexImgService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('admin:indexImg:page')")
    public Result<Page<IndexImg>> loadIndexImg(Page<IndexImg> page,IndexImg indexImg){
        return Result.success(indexImgService.loadIndexImg(page,indexImg));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:indexImg:save')")
    public Result<Integer> addIndexImg(@RequestBody IndexImg indexImg){
        return Result.success(indexImgService.addIndexImg(indexImg));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:indexImg:delete')")
    public Result<Integer> deleteIndexImg(@RequestBody List<Long> ids){
        return Result.success(indexImgService.deleteByIds(ids));
    }

    @GetMapping("/indexImgs")
    public Result<List<IndexImg>> loadMallIndexImgs()
    {
        return Result.success(indexImgService.loadMallIndexImgs());
    }
}
