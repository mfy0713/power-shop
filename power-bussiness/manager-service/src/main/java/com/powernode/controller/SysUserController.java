package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.ann.MyLog;
import com.powernode.domain.LoginSysUser;
import com.powernode.domain.SysUser;
import com.powernode.model.Result;
import com.powernode.service.SysUserService;
import com.powernode.util.AuthUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "系统管理员接口")
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/info")
    @MyLog(operation = "获取当前登录用户信息")
    public Result<LoginSysUser> getUserInfo() {
        return Result.success(AuthUtil.getSysUser());
    }

    @GetMapping("/info/{sysUserId}")
    @MyLog(operation = "查询某个用户的详情以及角色")
    public Result<SysUser> getSysUserById(@PathVariable Long sysUserId) {
        return Result.success(sysUserService.loadSysUserWithRoleIds(sysUserId));
    }

    @GetMapping("/page")
    public Result<Page<SysUser>> loadSysUserWithPage(Page<SysUser> page, SysUser sysUser) {
        return Result.success(sysUserService.loadSysUserWithPage(page, sysUser));

    }

    @PostMapping
    public Result<Integer> addSysUser(@RequestBody SysUser sysUser) {
        return Result.success(sysUserService.addSysUser(sysUser));
    }

    @PutMapping
    @MyLog(operation = "更新用户信息")
    public Result<Integer> updateSysUser(@RequestBody SysUser sysUser) {
        return Result.success(sysUserService.updateSysUser(sysUser));
    }

    @DeleteMapping("/{sysUserId}")
    @MyLog(operation = "删除用户信息")
    public Result<Integer> deleteSysUser(@PathVariable Long sysUserId) {
        return Result.success(sysUserService.deleteById(sysUserId));
    }
}
