package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Prod;
import com.powernode.domain.SysUser;
import com.powernode.dto.ProdDto;
import com.powernode.model.Result;
import com.powernode.service.ProdService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/prod/prod")
@RestController
@Api(tags = "产品管理接口")
public class ProdController {
    @Autowired
    private ProdService prodService;

    @GetMapping("/page")
    public Result<Page<Prod>> loadProdPage(Page<Prod> page, Prod prod) {
        return Result.success(prodService.loadProdPage(page, prod));
    }

    @PostMapping
    public Result<Integer> addProd(@RequestBody ProdDto prodDto) {
        return Result.success(prodService.addProd(prodDto));
    }

    /*修改商品回显*/
    @GetMapping("/info/{prodId}")
    public Result<ProdDto> getSysUserById(@PathVariable Long prodId) {
        return Result.success(prodService.loadProdWithProdId(prodId));
    }

    @PutMapping
    public Result<Integer> updateProd(@RequestBody ProdDto prodDto){
        return Result.success(prodService.updateProd(prodDto));
    }



    /*删除商品*/
    @DeleteMapping("/{prodId}")
    public Result<Integer> deleteProd(@PathVariable Long prodId){
        return Result.success(prodService.deleteByPid(prodId));
    }
}
