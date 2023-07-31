package com.atorres.nttdata.yankimsf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@SpringBootApplication
public class YankiMsfApplication {

	public static void main(String[] args) {
		SpringApplication.run(YankiMsfApplication.class, args);
	}

}
