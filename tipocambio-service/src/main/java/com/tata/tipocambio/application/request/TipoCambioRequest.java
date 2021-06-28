package com.tata.tipocambio.application.request;

import java.math.BigDecimal;

import com.tata.tipocambio.application.request.validation.TipoCambioValid;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@TipoCambioValid
public class TipoCambioRequest {

	private BigDecimal monto;
	private String monedaOrigen;
	private String monedaDestino;
	private BigDecimal tipoCambio;
	
}
