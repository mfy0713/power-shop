package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProdTagService extends IService<ProdTag> {


    Page<ProdTag> loadProdTagPage(Page<ProdTag> prodTagPage, ProdTag prodTag);

    List<ProdTag> loadMallProdTagList();
}
