package org.springframework.samples.dpc.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ContrasenyaValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContrasenyaConstraint {
	String message() default "La contraseña debe tener al entre 8 y 16 caracteres,"
			+ " al menos un dígito, al menos una minúscula y al menos una mayúscula.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}