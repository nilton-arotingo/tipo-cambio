package com.tata.tipocambio.domain.entity;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MonedaEnum {

	SOLES("PEN", "Soles"), DOLARES("USD", "Dolares"), EUROS("EUR", "Euros");

	private String codigo;
	private String descripcion;

	public static MonedaEnum obtenerEnum(String codigo) {
		return Stream.of(MonedaEnum.values())
				.filter(moneda -> moneda.getCodigo().equals(codigo))
				.findFirst()
				.orElse(null);
	}

}