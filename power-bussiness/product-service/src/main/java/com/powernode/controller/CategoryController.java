package com.powernode.controller;

import com.powernode.domain.Category;
import com.powernode.model.Result;
import com.powernode.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/table")
    public Result<List<Category>> loadAllCategory(){
        return Result.success(categoryService.loadAllCategory());
    }

    @GetMapping("/listCategory")
    @ApiOperation("加载所有的类别")
    public Result<List<Category>> listCategory(){
        return Result.success(categoryService.loadAllCategory());
    }

    @PostMapping
    @ApiOperation("保存类别")
    public Result<Integer> saveCategory(@RequestBody Category category){
        return Result.success(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{categoryId}")
//    @MyLog(operation = "删除用户信息")
    public Result<Integer> deleteCategory(@PathVariable Long categoryId) {
        return Result.success(categoryService.deleteByCategoryId(categoryId));
    }
}
