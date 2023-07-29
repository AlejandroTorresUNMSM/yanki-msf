package com.atorres.nttdata.yankimsf.service;

import com.atorres.nttdata.yankimsf.client.FeignApiClient;
import com.atorres.nttdata.yankimsf.client.FeignApiDebit;
import com.atorres.nttdata.yankimsf.exception.CustomException;
import com.atorres.nttdata.yankimsf.model.CreateRequest;
import com.atorres.nttdata.yankimsf.model.YankiDto;
import com.atorres.nttdata.yankimsf.model.clientms.ClientDto;
import com.atorres.nttdata.yankimsf.model.dao.YankiDao;
import com.atorres.nttdata.yankimsf.repository.YankiRepository;
import com.atorres.nttdata.yankimsf.utils.YankiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class YankiService {
  @Autowired
  private YankiRepository yankiRepository;
  @Autowired
  private FeignApiClient feignApiClient;
  @Autowired
  private FeignApiDebit feignApiDebit;
  @Autowired
  private YankiMapper mapper;

  public Flux<YankiDto> getAll(){
    return yankiRepository.findAll()
        .map(mapper::toDto);
  }

  public Mono<YankiDto> create(CreateRequest request){
    return validate(request)
        .flatMap(yankiDao -> yankiRepository.save(yankiDao))
        .map(mapper::toDto);
  }

  private Mono<YankiDao> validate(CreateRequest request){
    return feignApiClient.getClientByPhone(request.getPhone())
        .single()
        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No existe el cliente")))
        .flatMap(client -> feignApiDebit.getDebitClient(client.getId())
            .filter(debit -> debit.getId().equals(request.getDebitId()))
            .single()
            .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No existe el debito")))
            .map(debit -> mapper.toDao(request,client.getName(),debit.getMainProduct())));
  }
}
