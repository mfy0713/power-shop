package com.powernode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Area;
import com.baomidou.mybatisplus.extension.service.IService;
import com.powernode.domain.MemberAddr;

import java.util.List;

public interface AreaService extends IService<Area>{


    List<Area> loadAreaList();

    Integer addMemberAddr(MemberAddr memberAddr);
}
