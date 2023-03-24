package com.powernode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.powernode.domain.Member;
import com.powernode.model.Result;
import com.powernode.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "会员管理API接口")
@RequestMapping("/p/user")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/getMembersByOpenIds")
    @ApiOperation("远程根据openIds调用会员信息")
    public List<Member> getMembersByOpenIds(@RequestParam List<String> openIds) {

        return memberService.list(new LambdaQueryWrapper<Member>()
                .in(Member::getOpenId, openIds));
    }

    //更改用户的头像和昵称等信息
    @PutMapping("/setUserInfo")
    public Result<Integer> updateMemberInfo(@RequestBody Member member) {
        return Result.success(memberService.updateMemberInfo(member));

    }
}
