package com.powernode.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.TransportMapper;
import com.powernode.domain.Transport;
import com.powernode.service.TransportService;
@Service
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements TransportService{

}
