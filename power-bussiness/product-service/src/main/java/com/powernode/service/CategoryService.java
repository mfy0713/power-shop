package com.powernode.service;

import com.powernode.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category>{

    List<Category> loadAllCategory();

    Integer saveCategory(Category category);

    Integer deleteByCategoryId(Long categoryId);
}
