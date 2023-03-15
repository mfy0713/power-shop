package com.powernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.constant.BaseConstant;
import com.powernode.domain.SysRole;
import com.powernode.domain.SysUserRole;
import com.powernode.mapper.SysRoleMapper;
import com.powernode.mapper.SysUserRoleMapper;
import com.powernode.service.SysRoleService;
import com.powernode.service.SysUserRoleService;
import com.powernode.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.domain.SysUser;
import com.powernode.mapper.SysUserMapper;
import com.powernode.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    public Page<SysUser> loadSysUserWithPage(Page<SysUser> page, SysUser sysUser) {
        //获取当前用户的店铺id
        Long shopId = AuthUtil.getShopId();
        return sysUserMapper.selectPage(page, new LambdaQueryWrapper<SysUser>()
                .eq(!shopId.equals(BaseConstant.ADMIN_ID), SysUser::getShopId, shopId)
                .like(StringUtils.hasText(sysUser.getUsername()), SysUser::getUsername, sysUser.getUsername())
                .orderByDesc(SysUser::getCreateTime));
    }

    @Override
    @Transactional
    public Integer addSysUser(SysUser sysUser) {
        sysUser.setCreateUserId(AuthUtil.getSysUserId());
        sysUser.setCreateTime(new Date());
        //将密码明文加密
        String pwdByt = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        sysUser.setPassword(pwdByt);
        sysUser.setShopId(AuthUtil.getShopId());
        //插入用户表
        int count = sysUserMapper.insert(sysUser);
        //构建用户角色关联表集合
        List<SysUserRole> userRoleList = new ArrayList<>();
        sysUser.getRoleIdList().forEach(rid -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(rid);
            userRole.setUserId(sysUser.getUserId());
            userRoleList.add(userRole);
        });
        if (count > 0) {
            //插入角色
            sysUserRoleService.saveBatch(userRoleList);
        }

        return count;
    }

    @Override
    public SysUser loadSysUserWithRoleIds(Long sysUserId) {
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (!ObjectUtils.isEmpty(sysUser)) {
            //查询角色列表
            List<SysUserRole> userRoleList = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, sysUserId));
            //获取角色id集合
            List<Long> roleIds = userRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            sysUser.setRoleIdList(roleIds);
        }
        return sysUser;
    }

    @Override
    @Transactional
    public Integer updateSysUser(SysUser sysUser) {
        //修改用户表
        int count = sysUserMapper.updateById(sysUser);
        //删除当前用户的所有角色
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, sysUser.getUserId()));
        //构建用户角色关联表集合
        List<SysUserRole> userRoleList = new ArrayList<>();
        sysUser.getRoleIdList().forEach(rid -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(rid);
            userRole.setUserId(sysUser.getUserId());
            userRoleList.add(userRole);
        });
        //插入新的角色列表
        sysUserRoleService.saveBatch(userRoleList);

        return count;
    }

    @Override
    public Integer deleteById(Long sysUserId) {
        //首先删除用户角色关联表
        int count = sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
        .eq(SysUserRole::getUserId,sysUserId));
        //删除用户表
        count=sysUserMapper.deleteById(sysUserId);
        return count;
    }
}
