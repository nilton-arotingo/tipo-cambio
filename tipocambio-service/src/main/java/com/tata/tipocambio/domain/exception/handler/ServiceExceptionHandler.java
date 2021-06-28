package com.tata.tipocambio.domain.exception.handler;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tata.tipocambio.domain.exception.ArgumentNotValidException;
import com.tata.tipocambio.domain.exception.ElementNotFoundException;
import com.tata.tipocambio.domain.exception.TipoCambioException;
import com.tata.tipocambio.domain.response.Resultado;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(TipoCambioException.class)
	public ResponseEntity<Resultado> manejarServiceException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Resultado.builder().mensaje(e.getMessage()).build());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Resultado.builder().mensaje(errors.get(0).getDefaultMessage()).build());
	}
	
	@ExceptionHandler(ElementNotFoundException.class)
	public ResponseEntity<Resultado> handleElementFound(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Resultado.builder().mensaje(e.getMessage()).build());
	}
	
	@ExceptionHandler(ArgumentNotValidException.class)
	public ResponseEntity<Resultado> handleArgumentIvalid(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Resultado.builder().mensaje(e.getMessage()).build());
	}

}
