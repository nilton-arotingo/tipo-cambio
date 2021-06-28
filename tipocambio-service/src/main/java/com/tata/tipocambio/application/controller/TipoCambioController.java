package com.tata.tipocambio.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tata.tipocambio.application.request.TipoCambioRequest;
import com.tata.tipocambio.application.response.TipoCambioResponse;
import com.tata.tipocambio.domain.exception.ArgumentNotValidException;
import com.tata.tipocambio.domain.response.Resultado;
import com.tata.tipocambio.domain.security.jwt.JWTUtil;
import com.tata.tipocambio.domain.service.ITipoCambioService;

@RestController
@RequestMapping("/tipocambio")
public class TipoCambioController {
	
	private static final String TOKEN = "token";
	
	private static final String USUARIO = "USUARIO";
	
	private static final String USUARIO_AD = "usuarioPersonalidado";
	
	@Autowired
	private ITipoCambioService tipoCambioService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@GetMapping
	public ResponseEntity<List<TipoCambioResponse>> listarTiposCambios() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(TOKEN, generarToken());
		return new ResponseEntity<>(tipoCambioService.listarTiposCambios(), headers, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<TipoCambioResponse> cambiarMoneda(HttpServletRequest httpRequest, @RequestBody @Valid TipoCambioRequest request) {
		validarToken(httpRequest.getHeader(TOKEN));
		return ResponseEntity.status(HttpStatus.OK).body(tipoCambioService.cambiarMoneda(request));
	}
	
	@PostMapping("/actualizacion")
	public ResponseEntity<Resultado> actualizarValorCambio(HttpServletRequest httpRequest, @RequestBody TipoCambioRequest request) {
		validarToken(httpRequest.getHeader(TOKEN));
		return ResponseEntity.status(HttpStatus.OK).body(tipoCambioService.actualizarTipoCambio(request));
	}
	
	/*
	 * EL usuario personalizado se tomaria de un Active Directory y en base se evaluara
	 * */
	private String generarToken() {
		Map<String, Object> sessionMap = new HashMap<>();
		sessionMap.put(USUARIO, USUARIO_AD);
		return jwtUtil.crearJWT(sessionMap).getJwt();
	}
	
	private void validarToken(String token) {
		try {
			if (!USUARIO_AD.equals(jwtUtil.getStringAttribute(token, USUARIO)))
				throw new ArgumentNotValidException("Usuario invalido");
		} catch (Exception e) {
			throw new ArgumentNotValidException("La sesion es invalida");
		}
	}

}
