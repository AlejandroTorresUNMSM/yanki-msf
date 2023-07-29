package com.atorres.nttdata.yankimsf.client;

import com.atorres.nttdata.yankimsf.model.debitms.DebitDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@ReactiveFeignClient(value = "debit-msf", url = "${debit.ms.url}/")
public interface FeignApiDebit {
	@GetMapping(value = "{debitId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<DebitDto> getDebit(@PathVariable String debitId);

	@GetMapping(value = "client/{clientId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<DebitDto> getDebitClient(@PathVariable String clientId);

	@GetMapping(value = "main-balance/{debitId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<BigDecimal> getMainProduct(@PathVariable String debitId);

	@GetMapping(value = "all-balance/{debitId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<BigDecimal> getAllBalance(@PathVariable String debitId);
}
