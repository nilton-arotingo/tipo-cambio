package com.tata.tipocambio.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tata.tipocambio.application.request.TipoCambioRequest;
import com.tata.tipocambio.application.response.TipoCambioResponse;
import com.tata.tipocambio.domain.entity.TipoCambio;
import com.tata.tipocambio.domain.entity.TipoCambioPK;
import com.tata.tipocambio.domain.exception.ElementNotFoundException;
import com.tata.tipocambio.domain.exception.TipoCambioException;
import com.tata.tipocambio.domain.response.Resultado;
import com.tata.tipocambio.infraestructure.repository.TipoCambioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TipoCambioService implements ITipoCambioService {
	
	private static final String TIPO_CAMBIO = "tipoCambio";
	
	private static final String VALOR_OPERACION = "valorOperacion";
	
	private static final String MONEDA_SOLES = "PEN";
	
	private static final String MONEDA_DOLARES = "USD";
	
	@Autowired
	private TipoCambioRepository tipoCambioRepository;
	
	@Override
	public List<TipoCambioResponse> listarTiposCambios() {
		
		try {
			List<TipoCambioResponse> result = new ArrayList<>();
			List<TipoCambio> tiposCambios = tipoCambioRepository.findAll();
			tiposCambios.forEach(tc -> {
				TipoCambioResponse tipoCambioResponse = TipoCambioResponse.builder()
						.monedaOrigen(tc.getTipoCambioPK().getMonedaOrigen())
						.monedaDestino(tc.getTipoCambioPK().getMonedaDestino())
						.tipoCambio(tc.getValorCambio()).build();
				result.add(tipoCambioResponse);
			});

			return result;			
		} catch (Exception e) {
			log.error("Error al listar: ", e);
			throw new TipoCambioException("Error al listar");
		}
	}
	
	@Override
	public TipoCambioResponse cambiarMoneda(TipoCambioRequest request) {
		
		try {
			List<TipoCambio> tiposCambios = tipoCambioRepository.findAll();
			Map< String, BigDecimal> valorCambioMap = obtenerValoresDeCambios(tiposCambios, 
					request.getMonedaOrigen().toUpperCase(), request.getMonedaDestino().toUpperCase());
			
			return TipoCambioResponse.builder()
					.monto(request.getMonto())
					.montoConTipoCambio(request.getMonto().multiply(valorCambioMap.get(VALOR_OPERACION)).setScale(3, BigDecimal.ROUND_HALF_UP))
					.monedaOrigen(request.getMonedaOrigen().toUpperCase())
					.monedaDestino(request.getMonedaDestino().toUpperCase())
					.tipoCambio(valorCambioMap.get(TIPO_CAMBIO).setScale(2, BigDecimal.ROUND_HALF_UP))
					.build();
			
		} catch (Exception e) {
			log.error("Error al realizar tipo cambio: ", e);
			throw new TipoCambioException("Error al realizar tipo cambio");
		}
	}
	
	@Override
	public Resultado actualizarTipoCambio(TipoCambioRequest request) {
		
		TipoCambio tipoCambio = tipoCambioRepository.findById(
						new TipoCambioPK(request.getMonedaOrigen().toUpperCase(), request.getMonedaDestino().toUpperCase()))
				.orElseThrow(()->new ElementNotFoundException("tipos monedas invalidos"));
		
		try {
			tipoCambio.setValorCambio(request.getTipoCambio());
			tipoCambio.setFechaModificacion(LocalDateTime.now());
			tipoCambio.setUsuarioModificacion("USRMDF");
			tipoCambioRepository.save(tipoCambio);
			
		} catch (Exception e) {
			log.error("Error al actualizar valor de cambio: ", e);
			throw new TipoCambioException("Error al actualizar"); 
		}
		return Resultado.builder().mensaje("Actualizacion exitosa!").build();
	}
	
	private Map<String, BigDecimal> obtenerValoresDeCambios(List<TipoCambio> tiposCambios, String monedaOrigen, String monedaDestino) {
		Map< String, BigDecimal> result = new HashMap<>();
		
		tiposCambios.forEach(tc -> {
			if (tc.getTipoCambioPK().getMonedaOrigen().equals(monedaOrigen)
					&& Arrays.asList(MONEDA_SOLES, MONEDA_DOLARES).contains(monedaOrigen)) {
				if (tc.getTipoCambioPK().getMonedaDestino().equals(monedaDestino)
						&& !MONEDA_SOLES.equals(monedaDestino)) {
					result.put(TIPO_CAMBIO, tc.getValorCambio());
					result.put(VALOR_OPERACION, new BigDecimal(1).divide(tc.getValorCambio(), 8, RoundingMode.HALF_UP));
				} else if (tc.getTipoCambioPK().getMonedaDestino().equals(monedaDestino)) {
					result.put(TIPO_CAMBIO, tc.getValorCambio());
					result.put(VALOR_OPERACION, tc.getValorCambio());
				}
			} else if (tc.getTipoCambioPK().getMonedaOrigen().equals(monedaOrigen)) {
				if (tc.getTipoCambioPK().getMonedaDestino().equals(monedaDestino)) {
					result.put(TIPO_CAMBIO, tc.getValorCambio());
					result.put(VALOR_OPERACION, tc.getValorCambio());
				}
			}
		});
		
		return result;
	}

}
