package com.atorres.nttdata.yankimsf.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaidRequest {
  /**
   * Numero de telefono del cliente
   */
  private String phone;
  /**
   * Monto a transferir
   */
  private BigDecimal amount;
  /**
   * Numero de telefono del destinatario
   */
  private String destination;
}
