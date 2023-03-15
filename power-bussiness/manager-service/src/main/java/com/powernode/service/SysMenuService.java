package com.powernode.service;

import com.powernode.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu>{
    //根据用户id获取菜单
    List<SysMenu> getMenusByUserId(Long sysUserId);


}
