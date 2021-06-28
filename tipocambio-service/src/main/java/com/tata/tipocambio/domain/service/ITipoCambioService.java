package com.tata.tipocambio.domain.service;

import java.util.List;

import com.tata.tipocambio.application.request.TipoCambioRequest;
import com.tata.tipocambio.application.response.TipoCambioResponse;
import com.tata.tipocambio.domain.response.Resultado;

public interface ITipoCambioService {
	
	TipoCambioResponse cambiarMoneda(TipoCambioRequest request);
	
	List<TipoCambioResponse> listarTiposCambios();
	
	Resultado actualizarTipoCambio(TipoCambioRequest request);

}
