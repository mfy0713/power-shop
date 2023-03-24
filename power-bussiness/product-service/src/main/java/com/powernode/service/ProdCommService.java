package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdComm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.powernode.vo.ProdCommInfoVo;
import com.powernode.vo.ProdCommVo;

public interface ProdCommService extends IService<ProdComm> {

    //查询某个商品的评论汇总信息
    ProdCommVo loadProdCommSummary(Long prodId);

    //查询某个商品的评论详情
    Page<ProdCommInfoVo> loadProdCommInfoPage(Page<ProdComm> page, Long prodId, Integer evaluate);
}
