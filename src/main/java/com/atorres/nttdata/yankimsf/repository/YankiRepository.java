package com.atorres.nttdata.yankimsf.repository;

import com.atorres.nttdata.yankimsf.model.dao.YankiDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface YankiRepository extends ReactiveMongoRepository<YankiDao, String> {
  Mono<YankiDao> findByPhone(String phone);
}
