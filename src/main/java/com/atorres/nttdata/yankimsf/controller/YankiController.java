package com.atorres.nttdata.yankimsf.controller;

import com.atorres.nttdata.yankimsf.model.CreateRequest;
import com.atorres.nttdata.yankimsf.model.PaidRequest;
import com.atorres.nttdata.yankimsf.model.PaidResponse;
import com.atorres.nttdata.yankimsf.model.YankiDto;
import com.atorres.nttdata.yankimsf.service.YankiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/yanki")
@Slf4j
public class YankiController {
  @Autowired
  private YankiService yankiService;

  /**
   * Metodo que trae todas las billeteras Yanki
   * @return lista de Yanki
   */
  @GetMapping(path = {"","/"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<YankiDto> getAllYanki(){
    return yankiService.getAll()
        .doOnNext(v-> log.info("Yanki encontrado con exito"));
  }

  /**
   * Metodo para crear una billetera Yanki
   * @param request request
   * @return YankiDto
   */
  @PostMapping(path = {"","/"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<YankiDto> create(@RequestBody CreateRequest request){
    return yankiService.create(request)
        .doOnSuccess(v -> log.info("Yanki creado con exito"));
  }

  /**
   * Metodo para hacer pagos a otra billetera Yanki
   * @param request request
   * @return  PaidResponse
   */
  @PostMapping(value = "/paid", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<PaidResponse> paid(@RequestBody PaidRequest request){
    return yankiService.paid(request)
        .doOnSuccess(v -> log.info("Tranferencia yanki con exito"));
  }

}
