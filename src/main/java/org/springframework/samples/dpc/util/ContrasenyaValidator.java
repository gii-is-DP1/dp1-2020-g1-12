package org.springframework.samples.dpc.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContrasenyaValidator implements ConstraintValidator<ContrasenyaConstraint, String>{

	@Override
	public void initialize(ContrasenyaConstraint contrasenya) {
	}

	@Override
	public boolean isValid(String passwordField, ConstraintValidatorContext ctx) {
		return passwordField != null && passwordField.length() >= 8 && passwordField.length() < 17;
//				&& passwordField.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])$");
	}

}
