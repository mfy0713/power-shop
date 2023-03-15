package com.powernode.util;


import com.powernode.domain.LoginSysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * 获取用户身份信息
 */
public class AuthUtil {

    //获取用户id

    public static Long getSysUserId() {

        //获取安全上下文的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前登录的用户
        LoginSysUser loginSysUser = (LoginSysUser) authentication.getPrincipal();
        return loginSysUser.getUserId();
    }

    //当前用户的店铺
    public static Long getShopId() {
        //获取安全上下文的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前登录的用户
        LoginSysUser loginSysUser = (LoginSysUser) authentication.getPrincipal();
        return loginSysUser.getShopId();
    }


    public static LoginSysUser getSysUser() {

        //获取安全上下文的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前登录的用户
        LoginSysUser loginSysUser = (LoginSysUser) authentication.getPrincipal();
        return loginSysUser;
    }

    public static Set<String> getUserPerms() {

        //获取安全上下文的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前登录的用户
        LoginSysUser loginSysUser = (LoginSysUser) authentication.getPrincipal();
        return loginSysUser.getPerms();
    }
}
