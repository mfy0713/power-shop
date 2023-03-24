package com.powernode.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.powernode.config.WxAuthConfig;
import com.powernode.constant.AuthConstant;
import com.powernode.domain.LoginMember;
import com.powernode.domain.LoginSysUser;
import com.powernode.mapper.LoginMemberMapper;
import com.powernode.mapper.LoginSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

/**
 * 管理员和会员使用同一套登录程序，因此在进行登录时需要判断登陆类型，然后处理不同的数据库表
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private LoginSysUserMapper sysUserMapper;
    @Autowired
    private WxAuthConfig wxAuthConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoginMemberMapper loginMemberMapper;

    @Override   //自定义登录逻辑
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户的登录类型
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String loginType = request.getHeader(AuthConstant.LOGIN_TYPE);
        if (!StringUtils.hasText(loginType)) {
            throw new RuntimeException("非法的登录类型，是否没有提供loginType");
        }
        switch (loginType) {
            //管理员--sysUser表
            case AuthConstant.SYS_USER: {
                LoginSysUser loginSysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>()
                        .eq(LoginSysUser::getUsername, username));
                if (!ObjectUtils.isEmpty(loginSysUser)) {
                    //获取用户的权限perms
                    Set<String> perms = sysUserMapper.selectPermsByUserId(loginSysUser.getUserId());
                    //给当前用户复制权限，并处理,
                    loginSysUser.setPerms(perms);
                }
                return loginSysUser;
            }
            case AuthConstant.MEMBER: {
                String appId = wxAuthConfig.getAppId();
                String appSecret = wxAuthConfig.getAppSecret();
                String tokenUrl = wxAuthConfig.getWxTokenUrl();
                //获取真正的url
                String realUrl = String.format(tokenUrl, appId, appSecret, username);
                //向微信服务发送请求发送请求，获取openid
                String resultStr = restTemplate.getForObject(realUrl, String.class);
                //转换字符串为json对象
                JSONObject jsonObject = JSON.parseObject(resultStr);
                String openId = jsonObject.getString("openid");
                if (!StringUtils.hasText(openId)) {
                    throw new IllegalArgumentException("参数不正确，请检查配置");
                }
                //根据openid查询member表，判断用户是否注册过
                LoginMember loginMember = loginMemberMapper.selectOne(new LambdaQueryWrapper<LoginMember>()
                        .eq(LoginMember::getOpenId, openId));
                if (ObjectUtils.isEmpty(loginMember)) {
                    //用户第一次访问，则插入数据库表
                    loginMember = new LoginMember();
                    loginMember.setUserRegip(request.getRemoteAddr());
                    loginMember.setStatus(1);
                    loginMember.setOpenId(openId);
                    loginMember.setUserLasttime(new Date());
                    loginMember.setUpdateTime(new Date());
                    loginMember.setCreateTime(new Date());
                    loginMember.setUserLastip(request.getRemoteAddr());
                    //插入
                    loginMemberMapper.insert(loginMember);
                    return loginMember;
                } else    //已经存在此会员，则修改最后登录时间和ip
                {
                    loginMember.setUserLastip(request.getRemoteAddr());
                    loginMember.setUserLasttime(new Date());
                    //修改表
                    loginMemberMapper.updateById(loginMember);
                }
                return loginMember;
            }
        }


        return null;
    }

    public static void main(String[] args) {
        String result = new BCryptPasswordEncoder().encode("WECHAT");
        System.out.println(result);
    }
}
