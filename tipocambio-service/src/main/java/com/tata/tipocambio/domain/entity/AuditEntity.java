package com.tata.tipocambio.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditEntity<T> {
	
	@Column(name="CH_ESTADO")
	private boolean estado;	
	
	@Column(name="DT_FECHACREA")
	private LocalDateTime fechaCreacion;
	
	@Column(name="VC_USUARIOCREA")
	private String usuarioCreacion;
	
	@Column(name="DT_FECHAMOD")
	private LocalDateTime fechaModificacion;
	
	@Column(name="VC_USUARIOMOD")
	private String usuarioModificacion;

}
