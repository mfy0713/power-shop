package com.powernode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.powernode.domain.LoginSysUser;

import java.util.Set;

public interface LoginSysUserMapper extends BaseMapper<LoginSysUser> {
    //根据用户id获取用户的权限
    Set<String> selectPermsByUserId(Long userId);
}
