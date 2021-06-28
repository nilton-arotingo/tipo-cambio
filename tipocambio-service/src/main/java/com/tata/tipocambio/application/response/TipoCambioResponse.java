package com.tata.tipocambio.application.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class TipoCambioResponse {
	
	private BigDecimal monto;
	private BigDecimal montoConTipoCambio;
	private String monedaOrigen;
	private String monedaDestino;
	private BigDecimal tipoCambio;

}
