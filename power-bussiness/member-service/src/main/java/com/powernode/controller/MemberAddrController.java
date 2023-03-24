package com.powernode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.powernode.domain.MemberAddr;
import com.powernode.model.Result;
import com.powernode.service.MemberAddrService;
import com.powernode.util.AuthUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "会员地址管理API")
@RequestMapping("/p/address")
public class MemberAddrController {
    @Autowired
    private MemberAddrService memberAddrService;

    @GetMapping("/list")
    public Result<List<MemberAddr>> loadMemberAddr() {
        return Result.success(memberAddrService.list(new LambdaQueryWrapper<MemberAddr>()
                .eq(MemberAddr::getOpenId, AuthUtil.getMemberOpenId())
                .orderByDesc(MemberAddr::getCommonAddr)));
    }
}
