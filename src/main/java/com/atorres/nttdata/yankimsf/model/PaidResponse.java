package com.atorres.nttdata.yankimsf.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaidResponse {
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
  /**
   * Dia del pago
   */
  private Date date;
}
