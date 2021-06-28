package com.tata.tipocambio.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TipoCambioPK implements Serializable {

	private static final long	serialVersionUID	= 1L;
	
	@Column(name="ID_MONEDAORIGEN")
	private String monedaOrigen;
	
	@Column(name="ID_MONEDADESTINO")
	private String monedaDestino;

}
