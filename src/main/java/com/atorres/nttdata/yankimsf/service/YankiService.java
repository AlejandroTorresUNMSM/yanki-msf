package com.atorres.nttdata.yankimsf.service;

import com.atorres.nttdata.yankimsf.client.FeignApiAccount;
import com.atorres.nttdata.yankimsf.client.FeignApiClient;
import com.atorres.nttdata.yankimsf.client.FeignApiDebit;
import com.atorres.nttdata.yankimsf.exception.CustomException;
import com.atorres.nttdata.yankimsf.model.CreateRequest;
import com.atorres.nttdata.yankimsf.model.PaidRequest;
import com.atorres.nttdata.yankimsf.model.PaidResponse;
import com.atorres.nttdata.yankimsf.model.YankiDto;
import com.atorres.nttdata.yankimsf.model.accountms.AccountDto;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
  private FeignApiAccount feignApiAccount;
  @Autowired
  private YankiMapper mapper;

  /**
   * Metodo para traer todos los monederos yanki
   * @return lista yanki
   */
  public Flux<YankiDto> getAll(){
    return yankiRepository.findAll()
        .map(mapper::toDto);
  }

  /**
   * Metodo para crear un monedero Yanki
   * @param request request
   * @return yankiDto
   */
  public Mono<YankiDto> create(CreateRequest request){
    return validate(request)
        .flatMap(yankiDao -> yankiRepository.save(yankiDao))
        .map(mapper::toDto);
  }

  /**
   * Metodo para transferir a otro monedero Yanki
   * @param request request pago
   * @return vacio
   */
  public Mono<PaidResponse> paid(PaidRequest request) {
    String sourcePhone = request.getPhone();
    String destinationPhone = request.getDestination();
    //Obtenemos los YankiDao
    Mono<YankiDao> yankiFrom = yankiRepository.findByPhone(sourcePhone)
        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No tiene monedero Yanki")));
    Mono<YankiDao> yakiTo = yankiRepository.findByPhone(destinationPhone)
        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No se encontró la billetera de destinatario")));

    return Mono.zip(yankiFrom, yakiTo)
        .flatMap(tuple -> {
          YankiDao sourceYankiDao = tuple.getT1();
          YankiDao destinationYankiDao = tuple.getT2();
          //Obtenemos las cuentas
          Mono<AccountDto> accountFrom = feignApiAccount.getAccount(sourceYankiDao.getAccountId()).single();
          Mono<AccountDto> accountTo = feignApiAccount.getAccount(destinationYankiDao.getAccountId()).single();

          return Mono.zip(accountFrom, accountTo)
              .map(accountTuple -> modifyMapAccount(accountTuple.getT1(), accountTuple.getT2(), request.getAmount()))
              .map(mapacc -> new ArrayList<>(mapacc.values()))
              .flatMap(listAccount -> Flux.fromIterable(listAccount)
                  .flatMap(account -> feignApiAccount
                      .updateAccount(mapper.toUpdateAccount(account.getBalance(), account.getId()))
                      .then())
                  .then());
        })
        .thenReturn(mapper.toResponse(request));
  }

  /**
   * Metod que actualiza las cuentas segun el monto
   * @param accountFrom cuenta from
   * @param accountTo cuenta to
   * @param amount monto
   * @return map cuentas
   */
  private Map<String, AccountDto> modifyMapAccount(AccountDto accountFrom, AccountDto accountTo, BigDecimal amount){
    Map<String, AccountDto> mapAccount = new HashMap<>();
    accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
    log.info("Cuenta from : {}",accountFrom);
    accountTo.setBalance(accountTo.getBalance().add(amount));
    log.info("Cuenta to : {}",accountTo);
    //Seteamos las cuentas actualizadas en el Map
    mapAccount.put(accountFrom.getId(), accountFrom);
    mapAccount.put(accountTo.getId(), accountTo);
    return mapAccount;
  }

  /**
   * Validacion si es posible crear un monedero Yanki
   * @param request request
   * @return yankiDto
   */
  private Mono<YankiDao> validate(CreateRequest request){
    return feignApiClient.getClientByPhone(request.getPhone())
        .next()
        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No existe el cliente")))
        .flatMap(client -> feignApiDebit.getDebit(request.getDebitId()).single()
            .filter(debitDto -> debitDto.getClient().equals(client.getId()))
            .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No existe el debito")))
            .map(debit -> mapper.toDao(request,client.getName(),debit.getMainProduct())));
  }
}
