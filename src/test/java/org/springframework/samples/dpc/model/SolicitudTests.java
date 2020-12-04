package org.springframework.samples.dpc.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class SolicitudTests {
	
	private static final int TEST_SOLICITUD_ID = 1;
	private Solicitud solicitud;
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	@BeforeEach
	void setup() {

		solicitud = new Solicitud();
		this.solicitud.setId(TEST_SOLICITUD_ID);
		this.solicitud.setDescripcion("Solicitud de venta de MSI Prestige 14 Evo A11M-003ES");
		this.solicitud.setGastoEnvio(5.0);
		this.solicitud.setMarca("MSI Prestige");
		this.solicitud.setModelo("14 Evo A11M-003ES");
		this.solicitud.setPrecio(988.99);
		this.solicitud.setRespuesta("");
		this.solicitud.setSituacion(Situacion.Aceptada);
		this.solicitud.setStock(5);
		this.solicitud.setTiempoEntrega(8);
		this.solicitud.setTipo(Tipo.Nuevo);
		this.solicitud.setUrlImagen("https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg");
	}
	@Test
	void descripcionValidacion() {

		this.solicitud.setDescripcion("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).contains("La descripción debe contener entre 3 y 250 caracteres.");
	}


}
