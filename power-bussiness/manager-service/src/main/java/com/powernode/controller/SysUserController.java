package com.powernode.controller;

import com.powernode.domain.LoginSysUser;
import com.powernode.model.Result;
import com.powernode.util.AuthUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "系统管理员接口")
@RequestMapping("/sys/user")
public class SysUserController {

    @GetMapping("/info")
    public Result<LoginSysUser> getUserInfo(){
        return Result.success(AuthUtil.getSysUser());
    }
}
