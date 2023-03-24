package com.powernode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
    * 用户表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`member`")
public class LoginMember implements Serializable , UserDetails {
    /**
     * 会员表的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * ID
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 注册时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 注册IP
     */
    @TableField(value = "user_regip")
    private String userRegip;

    /**
     * 最后登录时间
     */
    @TableField(value = "user_lasttime")
    private Date userLasttime;

    /**
     * 最后登录IP
     */
    @TableField(value = "user_lastip")
    private String userLastip;


    /**
     * 状态 1 正常 0 无效
     */
    @TableField(value = "`status`")
    private Integer status;



    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override   //前端传递的是WECHAT,使用BCryptPasswordEncoder()加密
    public String getPassword() {
        return "$2a$10$LbE.lj2Dhgae56M2DllF1u73ebwqs9.vIoSHEayFZ.U25O8y/xDRC";
    }

    @Override
    public String getUsername() {
        return this.openId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status==1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status==1;
    }

    @Override
    public boolean isEnabled() {
        return this.status==1;
    }
}
