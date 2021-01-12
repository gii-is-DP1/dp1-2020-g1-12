package org.springframework.samples.dpc.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ValoracionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValoracionConstraint {
	String message() default "La valoración debe ser un número entre 1 y 5";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
