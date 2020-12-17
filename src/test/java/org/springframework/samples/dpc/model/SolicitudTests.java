package org.springframework.samples.dpc.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		assertThat(violation.getPropertyPath().toString()).hasToString("descripcion");
		assertThat(violation.getMessage()).contains("La descripción debe contener entre 3 y 250 caracteres.");
	}
	
	@Test
	void modeloValidacion() {

		this.solicitud.setModelo("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("modelo");
		assertThat(violation.getMessage()).contains("El modelo debe contener entre 3 y 200 caracteres.");
	}
	
	@Test
	void marcaValidacion() {

		this.solicitud.setMarca("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("marca");
		assertThat(violation.getMessage()).contains("La marca debe contener entre 3 y 50 caracteres.");
	}

	@Test
	void UrlVacíaValidacion() {

		this.solicitud.setUrlImagen("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("urlImagen");
		assertThat(violation.getMessage()).contains("La Url no puede estar vacía.");
	}
	
	@Test
	void UrlNoVálidaValidacion() {

		this.solicitud.setUrlImagen("esto no es una url");

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("urlImagen");
		assertThat(violation.getMessage()).contains("URL");
	}
	
	@Test
	void precioNullValidacion() {

		this.solicitud.setPrecio(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("precio");
		assertThat(violation.getMessage()).contains("El precio no puede estar vacío.");
	}
	
	@Test
	void precioFueraDeRangoValidacion() {

		this.solicitud.setPrecio(10001.);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("precio");
		assertThat(violation.getMessage()).contains("10000");
	}

	@Test
	void stockNullValidacion() {

		this.solicitud.setStock(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("stock");
		assertThat(violation.getMessage()).contains("El stock no puede estar vacío.");
	}
	
	@Test
	void stockFueraDeRangoValidacion() {

		this.solicitud.setStock(0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("stock");
		assertThat(violation.getMessage()).contains("1");
	}
	
	@Test
	void TipoValidacion() {

		this.solicitud.setTipo(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("tipo");
		assertThat(violation.getMessage()).contains("El tipo no puede estar vacío.");
	}
	
	@Test
	void tiempoEntregaValidacion() {

		this.solicitud.setTiempoEntrega(40);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("tiempoEntrega");
		assertThat(violation.getMessage()).contains("30");
	}
	
	@Test
	void gastoEnvioValidacion() {

		this.solicitud.setGastoEnvio(-1.);

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("gastoEnvio");
		assertThat(violation.getMessage()).contains("0");
	}
	
	@Test
	void respuestaValidacion() {

		this.solicitud.setRespuesta("Mensaje demasiado extenso.Mensaje demasiado extenso.Mensaje demasiado extenso.Mensaje demasiado extenso.");

		Validator validator = createValidator();
		Set<ConstraintViolation<Solicitud>> constraintViolations = validator.validate(solicitud);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Solicitud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("respuesta");
		assertThat(violation.getMessage()).contains("100");
	}

}
