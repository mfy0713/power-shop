package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdComm;
import com.powernode.model.Result;
import com.powernode.service.ProdCommService;
import com.powernode.vo.ProdCommInfoVo;
import com.powernode.vo.ProdCommVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "评论接口")
@RequestMapping("/prod/prodComm")
public class ProdCommController {

    @Autowired
    private ProdCommService prodCommService;

    @GetMapping("/prodComm/prodCommData")
    public Result<ProdCommVo> loadProdCommSummary(Long prodId) {
        return Result.success(prodCommService.loadProdCommSummary(prodId));
    }

    @GetMapping("/prodComm/prodCommPageByProd")
    public Result<Page<ProdCommInfoVo>> loadProdCommInfoPage(Page<ProdComm> page,Long prodId,Integer evaluate){
        return Result.success(prodCommService.loadProdCommInfoPage(page, prodId, evaluate));
    }
}
