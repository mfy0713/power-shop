package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.constant.ProdTagConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.ProdTagMapper;
import com.powernode.domain.ProdTag;
import com.powernode.service.ProdTagService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.ProdTagServiceImpl")
public class ProdTagServiceImpl extends ServiceImpl<ProdTagMapper, ProdTag> implements ProdTagService {
    @Autowired
    private ProdTagMapper prodTagMapper;

    @Override
    public Page<ProdTag> loadProdTagPage(Page<ProdTag> prodTagPage, ProdTag prodTag) {
        return prodTagMapper.selectPage(prodTagPage, new LambdaQueryWrapper<ProdTag>()
                .like(StringUtils.hasText(prodTag.getTitle()), ProdTag::getTitle, prodTag.getTitle())
                .eq(!ObjectUtils.isEmpty(prodTag.getStatus()), ProdTag::getStatus, prodTag.getStatus()));
    }

    @Override
    @Cacheable(key = ProdTagConstant.PROD_TAG_ALL_KEY)
    public List<ProdTag> loadMallProdTagList() {
        return prodTagMapper.selectList(new LambdaQueryWrapper<ProdTag>()
                .select(ProdTag::getTitle, ProdTag::getId, ProdTag::getStyle)
                .eq(ProdTag::getStatus, 1)
                .orderByDesc(ProdTag::getSeq));
    }
}
