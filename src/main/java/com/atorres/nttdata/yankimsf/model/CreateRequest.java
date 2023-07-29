package com.atorres.nttdata.yankimsf.model;

import lombok.Data;

@Data
public class CreateRequest {
  private String phone;
  private String imei;
  private String email;
  private String accountId;
}
