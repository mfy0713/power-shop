package com.powernode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.powernode.model.ProdCommStatics;
import com.powernode.domain.ProdComm;
import com.powernode.vo.ProdCommVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdCommMapper extends BaseMapper<ProdComm> {

    List<ProdCommStatics> selectProdCommStatics(@Param("prodIds") List<Long> prodIds);

    ProdCommVo selectProdCommSummary(Long prodId);
}
