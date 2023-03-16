package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Prod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.powernode.dto.ProdDto;

public interface ProdService extends IService<Prod>{


    Page<Prod> loadProdPage(Page<Prod> page, Prod prod);

    Integer addProd(ProdDto prodDto);
}
