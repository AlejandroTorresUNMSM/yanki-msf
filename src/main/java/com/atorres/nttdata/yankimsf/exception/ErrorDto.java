package com.atorres.nttdata.yankimsf.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {
	private HttpStatus httpStatus;
	private String message;
}
