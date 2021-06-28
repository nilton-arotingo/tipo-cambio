package com.tata.tipocambio.domain.exception;

public class TipoCambioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TipoCambioException(String mensaje) {
		super(mensaje);
	}

}
