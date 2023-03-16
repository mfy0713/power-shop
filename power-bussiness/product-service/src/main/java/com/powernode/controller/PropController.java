package com.powernode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdProp;
import com.powernode.domain.ProdPropValue;
import com.powernode.model.Result;
import com.powernode.service.ProdPropService;
import com.powernode.service.ProdPropValueService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod/spec")
@Api(tags = "属性规格接口")
public class PropController {
    @Autowired
    private ProdPropService prodPropService;
    @Autowired
    private ProdPropValueService prodPropValueService;

    @GetMapping("/page")
    public Result<Page<ProdProp>> loadProdPropWithValues(Page<ProdProp> page, ProdProp prodProp) {
        return Result.success(prodPropService.loadPropWithValues(page, prodProp));
    }

    @PostMapping()
    public Result<Integer> addProp(@RequestBody ProdProp prodProp) {
        return Result.success(prodPropService.addPropProp(prodProp));
    }

    @GetMapping("/list")
    public Result<List<ProdProp>> loadPropList() {
        return Result.success(prodPropService.list());
    }

    @GetMapping("/listSpecValue/{propId}")
    public Result<List<ProdPropValue>> listPropValueByProdId(@PathVariable Long propId) {
        return Result.success(prodPropValueService.list(new LambdaQueryWrapper<ProdPropValue>()
                .eq(ProdPropValue::getPropId, propId)));
    }


}
