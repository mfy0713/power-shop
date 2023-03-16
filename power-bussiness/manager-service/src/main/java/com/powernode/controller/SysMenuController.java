package com.powernode.controller;

import com.powernode.ann.MyLog;
import com.powernode.domain.LoginSysUser;
import com.powernode.domain.SysMenu;
import com.powernode.model.Result;
import com.powernode.service.SysMenuService;
import com.powernode.util.AuthUtil;
import com.powernode.vo.MenuAndAuth;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "菜单管理接口")
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/nav")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @MyLog(operation = "查看菜单列表")
    public Result<MenuAndAuth> getMenusAndAuth() {
        //获取用户菜单
        List<SysMenu> menuList = sysMenuService.getMenusByUserId(AuthUtil.getSysUserId());
        //获取用户权限
        Set<String> perms = AuthUtil.getUserPerms();
        //构建MenuAndAuth
        MenuAndAuth menuAndAuth = new MenuAndAuth(perms, menuList);
        return Result.success(menuAndAuth);
    }
}
