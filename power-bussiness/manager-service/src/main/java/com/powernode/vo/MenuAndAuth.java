package com.powernode.vo;

import com.powernode.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@ApiModel("界面展示用的菜单实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuAndAuth {
    @ApiModelProperty("当前用户权限集合")
    private Set<String> authorities;
    @ApiModelProperty("当前用户菜单集合")
    private List<SysMenu> menuList;
}
