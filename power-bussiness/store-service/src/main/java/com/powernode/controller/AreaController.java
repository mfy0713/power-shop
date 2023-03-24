package com.powernode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Area;
import com.powernode.domain.MemberAddr;
import com.powernode.domain.Notice;
import com.powernode.model.Result;
import com.powernode.service.AreaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "区域管理Api")
@RequestMapping("/admin/area")
@RestController
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/list")
    public Result<List<Area>> loadAreaList() {
        return Result.success(areaService.loadAreaList());
    }

    @GetMapping("/listByPid")
    public Result<List<Area>> loadAreaByPid(Long pid) {
        return Result.success(areaService.list(new LambdaQueryWrapper<Area>()
                .eq(Area::getParentId, pid)));
    }

    @PostMapping("")
    public Result<Integer> addMemberAddr(@RequestBody MemberAddr memberAddr){
        return Result.success(areaService.addMemberAddr(memberAddr));
    }

}
