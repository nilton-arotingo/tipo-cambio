package com.tata.tipocambio.application.request.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy ={TipoCambioValidation.class})
public @interface TipoCambioValid {
	
	String message() default "{com.tata.validacion.tipocambio.message}";
    
    Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
