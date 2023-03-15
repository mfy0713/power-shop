package com.powernode.service.impl;

import com.alibaba.fastjson.JSON;
import com.powernode.constant.MenuConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.domain.SysMenu;
import com.powernode.mapper.SysMenuMapper;
import com.powernode.service.SysMenuService;
import org.springframework.util.StringUtils;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<SysMenu> getMenusByUserId(Long sysUserId) {
        //从redis中获取
        String menuStr = redisTemplate.opsForValue().get(MenuConstant.SYS_MENU_PREFIX + sysUserId);
        if (!StringUtils.hasText(menuStr)) {
            //获取当前用户所有的菜单
            List<SysMenu> menuList = sysMenuMapper.selectMenusByUserId(sysUserId);
            //构建菜单的父子关系
            List<SysMenu> treeMenu = buildTreeMenuDg(0L, menuList);
            //写入redis--菜单转换为json写入redis
            redisTemplate.opsForValue().set(MenuConstant.SYS_MENU_PREFIX + sysUserId, JSON.toJSONString(treeMenu),
                    Duration.ofDays(Calendar.DAY_OF_WEEK));
            return treeMenu;
        } else {
            //如果redis已有菜单，则直接转换

            return JSON.parseArray(menuStr, SysMenu.class);

        }
    }

    //构建菜单的父子关系
    private List<SysMenu> buildTreeMenu(Long parentId, List<SysMenu> menuList) {
        //查询所有的父节点菜单
        List<SysMenu> roots = menuList.stream()
                .filter(sysMenu -> sysMenu.getParentId().equals(parentId))
                .collect(Collectors.toList());
        //循环构建每一个root的子菜单集合
        roots.forEach(root -> {
            List<SysMenu> children = new ArrayList<>();
            menuList.forEach(menu -> {
                if (menu.getParentId().equals(root.getMenuId())) {
                    children.add(menu);
                }
            });
            root.setList(children);
        });
        return roots;
    }

    //使用递归构建菜单
    public List<SysMenu> buildTreeMenuDg(Long parentId, List<SysMenu> sysMenus) {
        //构建根节点
        List<SysMenu> roots = sysMenus.stream()
                .filter(sysMenu -> sysMenu.getParentId().equals(parentId))
                .collect(Collectors.toList());
        //循环当前节点，以当前节点作为父节点构建子节点集合
        roots.forEach(root -> root.setList(buildTreeMenuDg(root.getMenuId(), sysMenus)));
        return roots;
    }
}
