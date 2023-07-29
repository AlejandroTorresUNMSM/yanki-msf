package com.atorres.nttdata.yankimsf.utils;

import com.atorres.nttdata.yankimsf.model.CreateRequest;
import com.atorres.nttdata.yankimsf.model.YankiDto;
import com.atorres.nttdata.yankimsf.model.dao.YankiDao;
import org.springframework.stereotype.Component;

@Component
public class YankiMapper {
  public YankiDto toDto(YankiDao yankiDao){
    YankiDto yanki = new YankiDto();
    yanki.setId(yankiDao.getId());
    yanki.setName(yankiDao.getName());
    yanki.setPhone(yankiDao.getPhone());
    yanki.setImei(yankiDao.getImei());
    yanki.setEmail(yankiDao.getEmail());
    yanki.setDebitId(yankiDao.getDebitId());
    yanki.setAccountId(yankiDao.getAccountId());
    return yanki;
  }

  public YankiDao toDao(CreateRequest request,String name,String mainAccount){
    YankiDao yanki = new YankiDao();
    yanki.setName(name);
    yanki.setPhone(request.getPhone());
    yanki.setImei(request.getImei());
    yanki.setEmail(request.getEmail());
    yanki.setDebitId(request.getDebitId());
    yanki.setAccountId(mainAccount);
    return  yanki;
  }
}
