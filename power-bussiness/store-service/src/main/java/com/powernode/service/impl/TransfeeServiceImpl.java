package com.powernode.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.TransfeeMapper;
import com.powernode.domain.Transfee;
import com.powernode.service.TransfeeService;
@Service
public class TransfeeServiceImpl extends ServiceImpl<TransfeeMapper, Transfee> implements TransfeeService{

}
