package com.powernode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.constant.AreaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.AreaMapper;
import com.powernode.domain.Area;
import com.powernode.service.AreaService;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.AreaServiceImpl")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    @Cacheable(key = AreaConstant.AREA_ALL_KEY)
    public List<Area> loadAreaList() {
        return areaMapper.selectList(null);
    }
}
