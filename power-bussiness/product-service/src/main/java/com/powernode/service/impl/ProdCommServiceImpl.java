package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Member;
import com.powernode.feign.MemberFeign;
import com.powernode.vo.ProdCommInfoVo;
import com.powernode.vo.ProdCommVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.ProdCommMapper;
import com.powernode.domain.ProdComm;
import com.powernode.service.ProdCommService;
import org.springframework.util.CollectionUtils;

@Service
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {

    @Autowired
    private ProdCommMapper prodCommMapper;
    @Autowired
    private MemberFeign memberFeign;

    @Override
    public ProdCommVo loadProdCommSummary(Long prodId) {
        ProdCommVo prodCommVo = prodCommMapper.selectProdCommSummary(prodId);
        if (prodCommVo.getNumber() == 0) {
            prodCommVo.setProdId(prodId);
            prodCommVo.setPositiveRating(BigDecimal.ZERO);
        } else {
            //好评率
            BigDecimal goodLv = new BigDecimal(prodCommVo.getPraiseNumber()).divide(
                    new BigDecimal(prodCommVo.getNumber()),
                    2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100));
            prodCommVo.setPositiveRating(goodLv);

        }
        return prodCommVo;
    }

    @Override
    public Page<ProdCommInfoVo> loadProdCommInfoPage(Page<ProdComm> page, Long prodId, Integer evaluate) {
        Page<ProdCommInfoVo> prodCommInfoVoPage = new Page<>(page.getCurrent(), page.getSize());
        //查询评论详情
        Page<ProdComm> prodCommPage = prodCommMapper.selectPage(page, new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(!evaluate.equals(-1), ProdComm::getEvaluate, evaluate)
                .orderByDesc(ProdComm::getCreateTime));
        //将ProdComm转换为ProdCommInfoVo---需要获取用户头像和昵称

        //获取当前评论页的记录中所有的OpenId
        List<ProdComm> prodCommList = prodCommPage.getRecords();
        if (CollectionUtils.isEmpty(prodCommList)) {
            return prodCommInfoVoPage;
        }
        List<String> openIds = prodCommPage.getRecords()
                .stream()
                .map(ProdComm::getOpenId)
                .collect(Collectors.toList());
        //远程调用
        List<Member> members = memberFeign.getMembersByOpenIds(openIds);
        //将ProdComm转换为ProdCommInfoVo
        List<ProdCommInfoVo> prodCommInfoVoList = new ArrayList<>();

        //循环
        prodCommList.forEach(prodComm -> {
            ProdCommInfoVo prodCommInfoVo = new ProdCommInfoVo();
            //属性拷贝
            BeanUtils.copyProperties(prodComm, prodCommInfoVo);
            //处理昵称头像
            if (!CollectionUtils.isEmpty(members)) {
                Member member = members.stream()
                        .filter(member1 -> member1.getOpenId().equals(prodComm.getOpenId()))
                        .collect(Collectors.toList())
                        .get(0);
                prodCommInfoVo.setPic(member.getPic());
                //脱敏处理
                String nickName = member.getNickName();
                if (nickName.length() == 1) {
                    prodCommInfoVo.setNickName(nickName + "***");
                } else {
                    StringBuilder sr = new StringBuilder(nickName);
                    StringBuilder replace = sr.replace(1, sr.length() - 1, "***");
                    prodCommInfoVo.setNickName(replace.toString());
                }
                //添加集合
                prodCommInfoVoList.add(prodCommInfoVo);

            }
        });
        prodCommInfoVoPage.setTotal(prodCommPage.getTotal());
        prodCommInfoVoPage.setRecords(prodCommInfoVoList);


        return prodCommInfoVoPage;
    }
}
