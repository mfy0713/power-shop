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
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class LoginSysUser implements Serializable, UserDetails {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

//    /**
//     * 邮箱
//     */
//    @TableField(value = "email")
//    private String email;
//
//    /**
//     * 手机号
//     */
//    @TableField(value = "mobile")
//    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField(value = "`status`")
    private Byte status;

//    /**
//     * 创建者ID
//     */
//    @TableField(value = "create_user_id")
//    private Long createUserId;
//
//    /**
//     * 创建时间
//     */
//    @TableField(value = "create_time")
//    private Date createTime;

    /**
     * 用户所在的商城Id
     */
    @TableField(value = "shop_id")
    private Long shopId;

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }

    //提供perms属性--获取用户权限，前端界面使用perms进行界面控制
    @TableField(exist = false)
    private Set<String> perms;

    /**
     * 数据库中有些权限包含，拆分多个权限
     *
     * @param myperms
     */
    public void setPerms(Set<String> myperms) {
        Set<String> realPerms = new HashSet<>();
        //循环每一个权限
        myperms.forEach(perm -> {
            //判断当前权限是否包含逗号，
            if (perm.contains(",")) {
                //拆分权限
                String[] strs = perm.split(",");
                for (String str : strs) {
                    //添加到目标集合
                    realPerms.add(str);
                }
            } else {
                //不包含都好，则直接添加
                realPerms.add(perm);
            }
        });
        this.perms = realPerms;
    }

}
