package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.constant.BaseConstant;
import com.powernode.constant.IndexImgConstant;
import com.powernode.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.domain.IndexImg;
import com.powernode.mapper.IndexImgMapper;
import com.powernode.service.IndexImgService;
import org.springframework.util.ObjectUtils;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.IndexImgServiceImpl")
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public Page<IndexImg> loadIndexImg(Page<IndexImg> page, IndexImg indexImg) {
        Long shopId = AuthUtil.getShopId();

        return indexImgMapper.selectPage(page, new LambdaQueryWrapper<IndexImg>()
                .eq(shopId != BaseConstant.ADMIN_ID, IndexImg::getShopId, shopId)
                .eq(!ObjectUtils.isEmpty(indexImg.getStatus()), IndexImg::getStatus, indexImg.getStatus())
                .orderByDesc(IndexImg::getSeq, IndexImg::getCreateTime));
    }

    @Override
    @CacheEvict(key= IndexImgConstant.INDEX_IMG_KEY)
    public Integer addIndexImg(IndexImg indexImg) {
        indexImg.setShopId(AuthUtil.getShopId());
        indexImg.setCreateTime(new Date());

        return indexImgMapper.insert(indexImg);
    }

    @CacheEvict(key = IndexImgConstant.INDEX_IMG_KEY)
    public Integer deleteByIds(List<Long> ids){
        return indexImgMapper.deleteBatchIds(ids);
    }
}
