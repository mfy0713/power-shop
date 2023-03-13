package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.powernode.constant.AuthConstant;
import com.powernode.domain.LoginSysUser;
import com.powernode.mapper.LoginSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 管理员和会员使用同一套登录程序，因此在进行登录时需要判断登陆类型，然后处理不同的数据库表
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private LoginSysUserMapper sysUserMapper;

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

            }
        }


        return null;
    }
}
