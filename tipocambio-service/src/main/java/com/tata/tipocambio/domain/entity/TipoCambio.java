package com.tata.tipocambio.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TIPOCAMBIO")
public class TipoCambio extends AuditEntity<TipoCambio> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TipoCambioPK tipoCambioPK;
	
	@Column(name="DE_VALORCAMBIO")
	private BigDecimal valorCambio;
	
}
