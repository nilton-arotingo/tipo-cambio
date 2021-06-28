package com.tata.tipocambio.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tata.tipocambio.domain.entity.TipoCambio;
import com.tata.tipocambio.domain.entity.TipoCambioPK;

public interface TipoCambioRepository extends JpaRepository<TipoCambio, TipoCambioPK> {

}
