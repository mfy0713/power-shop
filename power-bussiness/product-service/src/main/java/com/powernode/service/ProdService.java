package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Prod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.powernode.dto.ProdDto;
import com.powernode.model.ProdES;

import java.time.LocalDateTime;
import java.util.List;

public interface ProdService extends IService<Prod>{


    Page<Prod> loadProdPage(Page<Prod> page, Prod prod);

    Integer addProd(ProdDto prodDto);

    /**
     * 根据商品id返回商品信息
     * @param prodId
     * @return
     */
    ProdDto loadProdWithProdId(Long prodId);

    /**
     * 根据商品id删除商品
     * @param prodId
     * @return
     */
    Integer deleteByPid(Long prodId);

    /**
     * 更新商品信息
     * @param prodDto
     * @return
     */
    Integer updateProd(ProdDto prodDto);


    /**
     * 分页查询满足日期范围的商品数据
     * @param page
     * @param d1
     * @param d2
     * @return
     */
    List<ProdES> loadProdToProdES(Page<Prod> page, LocalDateTime d1,LocalDateTime d2);


    /**
     * 满足日期范围的商品记录数据
     * @param d1
     * @param d2
     * @return
     */
    Integer selectCount(LocalDateTime d1,LocalDateTime d2);
}
