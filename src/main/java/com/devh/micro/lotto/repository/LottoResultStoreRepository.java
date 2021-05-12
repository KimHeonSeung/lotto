package com.devh.micro.lotto.repository;

import com.devh.micro.lotto.entity.LottoResultStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LottoResultStoreRepository extends ElasticsearchRepository<LottoResultStore, String> {
}
