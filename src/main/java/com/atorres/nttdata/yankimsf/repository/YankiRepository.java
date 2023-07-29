package com.atorres.nttdata.yankimsf.repository;

import com.atorres.nttdata.yankimsf.model.dao.YankiDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface YankiRepository extends ReactiveMongoRepository<YankiDao, String> {
}
