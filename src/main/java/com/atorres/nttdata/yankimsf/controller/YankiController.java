package com.atorres.nttdata.yankimsf.controller;

import com.atorres.nttdata.yankimsf.service.YankiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yanki")
@Slf4j
public class YankiController {
  @Autowired
  private YankiService yankiService;
}
