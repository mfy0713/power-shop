package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdProp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProdPropService extends IService<ProdProp>{
    //加载属性以及属性值的集合
    Page<ProdProp> loadPropWithValues(Page<ProdProp> page,ProdProp prodProp);


    Integer addPropProp(ProdProp prodProp);
}
