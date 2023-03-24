package com.powernode.service.impl;

import com.powernode.domain.LoginMember;
import com.powernode.util.AuthUtil;
import com.powernode.util.WebUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.MemberMapper;
import com.powernode.domain.Member;
import com.powernode.service.MemberService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Integer updateMemberInfo(Member member) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        LoginMember loginMember = AuthUtil.getLoginMember();
        String nickname = member.getNickName();
        String sex = member.getSex();
        String pic = member.getPic();
        BeanUtils.copyProperties(loginMember, member);
        member.setNickName(nickname);
        member.setSex(sex);
        member.setPic(pic);
        member.setUserLasttime(new Date());
        //获取真实的ip地址
        member.setUserLastip(WebUtil.getIpAddress(request));

        return memberMapper.updateById(member);
    }
}
