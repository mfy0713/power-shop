package com.powernode.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.ProdTagReference;
import com.powernode.domain.Sku;
import com.powernode.dto.ProdDto;
import com.powernode.service.ProdTagReferenceService;
import com.powernode.service.SkuService;
import com.powernode.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.ProdMapper;
import com.powernode.domain.Prod;
import com.powernode.service.ProdService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService {

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @Override
    public Page<Prod> loadProdPage(Page<Prod> page, Prod prod) {
        return prodMapper.selectPage(page, new LambdaQueryWrapper<Prod>()
                .like(StringUtils.hasText(prod.getProdName()), Prod::getProdName, prod.getProdName())
                .eq(!ObjectUtils.isEmpty(prod.getStatus()), Prod::getStatus, prod.getStatus()));
    }

    @Override
    @Transactional
    public Integer addProd(ProdDto prodDto) {
        prodDto.setCreateTime(new Date());
        prodDto.setUpdateTime(new Date());
        prodDto.setVersion(1);
        prodDto.setShopId(AuthUtil.getShopId());
        if (prodDto.getStatus().equals(1)) {
            prodDto.setPutawayTime(new Date());
        }
        //配送模式--序列化为json
        prodDto.setDeliveryMode(JSON.toJSONString(prodDto.getDeliveryModeVo()));
        int count = prodMapper.insert(prodDto);
        if (count > 0) {
            //插入sku表
            List<Sku> skuList = prodDto.getSkuList();
            skuList.forEach(sku -> {
                sku.setProdId(prodDto.getProdId());
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                sku.setVersion(1);
            });
            //批量插入
            boolean flag = skuService.saveBatch(skuList);
            //插入prod_tag_perference
            List<ProdTagReference> tagReferences = new ArrayList<>();
            prodDto.getTagList().forEach(tagId -> {
                ProdTagReference reference = new ProdTagReference();
                reference.setTagId(tagId);
                reference.setProdId(prodDto.getProdId());
                reference.setShopId(AuthUtil.getShopId());
                reference.setStatus(true);
                //添加集合
                tagReferences.add(reference);
            });
            //批量插入refrence
            flag = prodTagReferenceService.saveBatch(tagReferences);

        }
        return count;
    }
}
