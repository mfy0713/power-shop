package com.powernode.dao;

import com.powernode.model.ProdES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdESDao extends ElasticsearchRepository<ProdES, Long> {
}
