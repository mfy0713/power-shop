package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.constant.NoticeConstant;
import com.powernode.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.NoticeMapper;
import com.powernode.domain.Notice;
import com.powernode.service.NoticeService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.NoticeServiceImpl")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Page<Notice> loadNotice(Page<Notice> page, Notice notice) {
        long shopId = AuthUtil.getShopId();
        return noticeMapper.selectPage(page, new LambdaQueryWrapper<Notice>()
                .eq(StringUtils.hasText(notice.getContent()), Notice::getContent, notice.getContent())
                .eq(!ObjectUtils.isEmpty(notice.getStatus()), Notice::getStatus, notice.getStatus())
                .eq(!ObjectUtils.isEmpty(notice.getIsTop()), Notice::getIsTop, notice.getIsTop())
                .orderByDesc(Notice::getCreateTime));
    }

    @Override
    @CacheEvict(key = NoticeConstant.NOTICE_ALL_KEY)
    public Integer addNotice(Notice notice) {
        notice.setShopId(AuthUtil.getShopId());
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        return noticeMapper.insert(notice);
    }

    @Override
    @Cacheable(key = NoticeConstant.NOTICE_ALL_KEY)
    public List<Notice> loadTopNoticeList() {
        return noticeMapper.selectList(new LambdaQueryWrapper<Notice>()
                .select(Notice::getTitle, Notice::getId)
                .eq(Notice::getStatus, 1)
                .eq(Notice::getIsTop, 1)
                .orderByDesc(Notice::getCreateTime));
    }
}
