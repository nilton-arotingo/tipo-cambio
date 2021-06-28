package com.tata.tipocambio.application.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.tata.tipocambio.application.request.TipoCambioRequest;
import com.tata.tipocambio.domain.entity.MonedaEnum;

public class TipoCambioValidation implements ConstraintValidator<TipoCambioValid, TipoCambioRequest> {

	@Override
	public boolean isValid(TipoCambioRequest value, ConstraintValidatorContext context) {
		
		if (StringUtils.isEmpty(value.getMonedaOrigen())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.tata.validacion.tipocambio.monedaOrigenRequired.message}").addConstraintViolation();
			return false;
		}
		
		if (StringUtils.isEmpty(value.getMonedaDestino())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.tata.validacion.tipocambio.monedaDestinoRequired.message}").addConstraintViolation();
			return false;
		}
		
		if (!StringUtils.isEmpty(value.getMonedaOrigen()) && MonedaEnum.obtenerEnum(value.getMonedaOrigen().toUpperCase()) == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.tata.validacion.tipocambio.monedaOrigenInvalid.message}").addConstraintViolation();
			return false;
		}
		
		if (!StringUtils.isEmpty(value.getMonedaDestino()) && MonedaEnum.obtenerEnum(value.getMonedaDestino().toUpperCase()) == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.tata.validacion.tipocambio.monedaDestinoInvalid.message}").addConstraintViolation();
			return false;
		}
		
		if (value.getMonedaOrigen().equalsIgnoreCase(value.getMonedaDestino())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.tata.validacion.tipocambio.monedasInvalid.message}").addConstraintViolation();
			return false;
		}
		
		return true;
	}
	
}
