package com.atorres.nttdata.yankimsf.utils;

import com.atorres.nttdata.yankimsf.model.CreateRequest;
import com.atorres.nttdata.yankimsf.model.PaidRequest;
import com.atorres.nttdata.yankimsf.model.PaidResponse;
import com.atorres.nttdata.yankimsf.model.YankiDto;
import com.atorres.nttdata.yankimsf.model.accountms.RequestUpdateAccount;
import com.atorres.nttdata.yankimsf.model.dao.YankiDao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

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

  public RequestUpdateAccount toUpdateAccount(BigDecimal balance, String from){
    RequestUpdateAccount request = new RequestUpdateAccount();
    request.setBalance(balance);
    request.setAccountId(from);
    return  request;
  }

  public PaidResponse toResponse(PaidRequest request){
    PaidResponse response = new PaidResponse();
    response.setPhone(request.getPhone());
    response.setDestination(request.getDestination());
    response.setAmount(request.getAmount());
    response.setDate(new Date());
    return response;
  }
}
