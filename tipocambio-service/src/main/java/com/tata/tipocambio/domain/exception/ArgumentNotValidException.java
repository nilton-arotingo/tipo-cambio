package com.tata.tipocambio.domain.exception;

public class ArgumentNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ArgumentNotValidException(String message){
	     super(message);
	  }

}
