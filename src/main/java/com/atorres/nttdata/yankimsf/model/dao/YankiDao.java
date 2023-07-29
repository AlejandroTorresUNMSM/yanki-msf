package com.atorres.nttdata.yankimsf.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("yankis")
public class YankiDao {
  @Id
  private String id;
  private String name;
  private String phone;
  private String imei;
  private String email;
  private String debitId;
  private String accountId;
}
