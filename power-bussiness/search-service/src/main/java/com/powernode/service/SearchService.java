package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.model.ProdES;

public interface SearchService {
    //根据标签查询es
    Page<ProdES> findByTagId(Integer tagId,Integer current,Integer size);
    //根据关键字高亮查询
    Page<ProdES> findByProdName(String prodName,Integer current,Integer size,Integer sort);
}
