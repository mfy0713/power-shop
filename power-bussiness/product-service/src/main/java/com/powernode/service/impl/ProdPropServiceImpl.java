package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.constant.BaseConstant;
import com.powernode.domain.ProdPropValue;
import com.powernode.mapper.ProdPropValueMapper;
import com.powernode.service.ProdPropValueService;
import com.powernode.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.ProdPropMapper;
import com.powernode.domain.ProdProp;
import com.powernode.service.ProdPropService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ProdPropServiceImpl extends ServiceImpl<ProdPropMapper, ProdProp> implements ProdPropService {
    @Autowired
    private ProdPropMapper prodPropMapper;  //属性

    @Autowired
    private ProdPropValueMapper prodPropValueMapper;    //属性值

    @Autowired
    private ProdPropValueService prodPropValueService;

    @Override
    public Page<ProdProp> loadPropWithValues(Page<ProdProp> page, ProdProp prodProp) {
        Long shopId = AuthUtil.getShopId();
        //获取满足条件的属性分页对象
        Page<ProdProp> prodPropPage = prodPropMapper.selectPage(page, new LambdaQueryWrapper<ProdProp>()
                .eq(!shopId.equals(BaseConstant.ADMIN_ID), ProdProp::getShopId, shopId)
                .like(StringUtils.hasText(prodProp.getPropName()), ProdProp::getPropName, prodProp.getPropName()));

        List<ProdProp> prodPropList = prodPropPage.getRecords();
        //获取所有属性的id集合
        List<Long> propIds = prodPropList.stream()
                .map(ProdProp::getPropId)
                .collect(Collectors.toList());
        //查询Prod_prop_value表中满足条件的所有的值的集合 select * from prod_prop_value where prop_id in (
        List<ProdPropValue> prodPropValues = prodPropValueMapper.selectList(new LambdaQueryWrapper<ProdPropValue>()
                .in(ProdPropValue::getPropId, propIds));
        //循环处理每一个属性对应的属性值的集合
        prodPropList.forEach(prodProp1 -> {
            List<ProdPropValue> prodPropValueList = prodPropValues.stream()
                    .filter(prodPropValue -> prodProp1.getPropId().equals(prodPropValue.getPropId()))
                    .collect(Collectors.toList());
            //赋值
            prodProp1.setProdPropValues(prodPropValueList);

        });
        return prodPropPage;
    }

    @Override
    @Transactional
    public Integer addPropProp(ProdProp prodProp) {
        //插入属性表
        prodProp.setShopId(AuthUtil.getShopId());
        prodProp.setRule(1);
        int count = prodPropMapper.insert(prodProp);
        if (count > 0) {
            //插入属性值---一次性插入集合
            List<ProdPropValue> prodPropValues=prodProp.getProdPropValues();
            prodPropValues.forEach(prodPropValue -> {
                prodPropValue.setPropId(prodProp.getPropId());
            });
            prodPropValueService.saveBatch(prodPropValues);
        }

        return count;
    }
}
