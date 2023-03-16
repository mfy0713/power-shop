package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.powernode.constant.CategoryConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.domain.Category;
import com.powernode.mapper.CategoryMapper;
import com.powernode.service.CategoryService;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Cacheable(key = CategoryConstant.ALL_CATEGORY_KEY)
    public List<Category> loadAllCategory() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .orderByDesc(Category::getSeq));
    }

    @Override
    @CacheEvict(key = CategoryConstant.ALL_CATEGORY_KEY)
    public Integer saveCategory(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        return categoryMapper.insert(category);
    }

    @Override
    @CacheEvict(key = CategoryConstant.ALL_CATEGORY_KEY)
    public Integer deleteByCategoryId(Long categoryId) {
        int count = categoryMapper.deleteById(categoryId);
        return count;
    }
}
