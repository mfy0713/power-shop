package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysUserService extends IService<SysUser>{


    Page<SysUser> loadSysUserWithPage(Page<SysUser> page, SysUser sysUser);

    Integer addSysUser(SysUser sysUser);

    //加载用户以及角色列表
    SysUser loadSysUserWithRoleIds(Long sysUserId);

    Integer updateSysUser(SysUser sysUser);

    Integer deleteById(Long sysUserId);
}
