package org.springframework.samples.dpc.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValoracionValidator implements ConstraintValidator<ValoracionConstraint, Integer>{
	
	@Override
	public void initialize(ValoracionConstraint valoracion) {
	}
	
	@Override
	public boolean isValid (Integer valoracionField , ConstraintValidatorContext cxt) {
		return valoracionField != null && valoracionField >= 0 && valoracionField < 6;
	}
}
