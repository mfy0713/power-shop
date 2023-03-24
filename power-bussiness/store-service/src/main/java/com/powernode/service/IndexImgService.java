package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.IndexImg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IndexImgService extends IService<IndexImg>{


    Page<IndexImg> loadIndexImg(Page<IndexImg> page, IndexImg indexImg);

    Integer addIndexImg(IndexImg indexImg);

    Integer deleteByIds(List<Long> ids);

    List<IndexImg> loadMallIndexImgs();
}
