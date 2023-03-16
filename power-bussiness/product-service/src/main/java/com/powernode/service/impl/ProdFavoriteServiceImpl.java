package com.powernode.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.powernode.mapper.ProdFavoriteMapper;
import com.powernode.domain.ProdFavorite;
import com.powernode.service.ProdFavoriteService;
@Service
public class ProdFavoriteServiceImpl extends ServiceImpl<ProdFavoriteMapper, ProdFavorite> implements ProdFavoriteService{

}
