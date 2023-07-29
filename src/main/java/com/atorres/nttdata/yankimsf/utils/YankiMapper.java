package com.atorres.nttdata.yankimsf.utils;

import com.atorres.nttdata.yankimsf.model.CreateRequest;
import com.atorres.nttdata.yankimsf.model.YankiDto;
import com.atorres.nttdata.yankimsf.model.dao.YankiDao;

public class YankiMapper {
  public YankiDto toDto(YankiDao yankiDao){
    YankiDto yanki = new YankiDto();
    yanki.setId(yankiDao.getId());
    yanki.setPhone(yankiDao.getPhone());
    yanki.setImei(yankiDao.getImei());
    yanki.setEmail(yankiDao.getEmail());
    yanki.setAccountId(yankiDao.getAccountId());
    return yanki;
  }

  public YankiDao toDao(CreateRequest request){
    YankiDao yanki = new YankiDao();
    yanki.setPhone(request.getPhone());
    yanki.setImei(request.getImei());
    yanki.setEmail(request.getEmail());
    yanki.setAccountId(request.getAccountId());
    return  yanki;
  }
}
