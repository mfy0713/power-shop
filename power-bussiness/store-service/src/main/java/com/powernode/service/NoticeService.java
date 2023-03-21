package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
public interface NoticeService extends IService<Notice>{


    Page<Notice> loadNotice(Page<Notice> page, Notice notice);

    Integer addNotice(Notice notice);
}
