package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.service.exceptions.BloquearSinDescripcionException;
import org.springframework.samples.dpc.service.exceptions.UsuarioBloqueadoException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class BloqueoServiceTest {

	private BloqueoService bloqueoService;
	private ClienteService clienteService;

	@Autowired
	public BloqueoServiceTest(BloqueoService bloqueoService,ClienteService clienteService) {
		this.bloqueoService = bloqueoService;
		this.clienteService = clienteService;
	}
	
	@ParameterizedTest
	@DisplayName("Test Editar bloqueo (Test parametrizado)")
	@ValueSource(strings = {"Bloqueado por intento de fraude", "Bloqueado por venta ilegal"})
	void testEdit(String descripcion) throws BloquearSinDescripcionException{
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		bloqueo.setDescripcion(descripcion);
		this.bloqueoService.editar(bloqueo, bloqueo.getId(), true);
		assertThat(bloqueo.isBloqueado()).isTrue();	
	}
	
	@ParameterizedTest
	@DisplayName("Test Editar bloqueo con error(Test parametrizado)")
	@ValueSource(strings = {"" , "Bloqueado"})
	void testEditConExcepcion(String descripcion) throws BloquearSinDescripcionException{
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		bloqueo.setDescripcion(descripcion);
		assertThrows(BloquearSinDescripcionException.class,
				() -> this.bloqueoService.editar(bloqueo, bloqueo.getId(), true));	
	}
	
	@Test
	@DisplayName("Test Quitar bloqueo")
	void testEditSinBloqueo() throws BloquearSinDescripcionException{
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		this.bloqueoService.editar(bloqueo, bloqueo.getId(), false);
		assertThat(bloqueo.isBloqueado()).isFalse();	
	}
	
	@Test
	@DisplayName("Test Guardar bloqueo")
	void testSave() throws BloquearSinDescripcionException{
		Bloqueo bloqueo = new Bloqueo();
		bloqueo.setBloqueado(true);
		bloqueo.setDescripcion("HOLA ME LLAMO PEPE");
		this.bloqueoService.guardar(bloqueo);
		assertThat(bloqueo.isBloqueado()).isTrue();	
	}
	
	@Test
	@DisplayName("Test Obtener usuario bloqueado ")
	void testBloqueado() throws UsuarioBloqueadoException{
		
		Cliente  bloqueado = clienteService.findAllClient(0, 10, "nombre").getContent().stream().filter(x -> x.getBloqueo().isBloqueado()==true).collect(Collectors.toList()).get(0);
		assertThrows(UsuarioBloqueadoException.class,
				() -> this.bloqueoService.usuarioBloqueado(bloqueado.getUser().getUsername()));
	}
	
	@Test
	@DisplayName("Test Obtener descripción del bloqueo")
	void testClienteBloqueado() throws UsuarioBloqueadoException{
		Cliente  bloqueado = clienteService.findAllClient(0, 10, "nombre").getContent().stream().filter(x -> x.getBloqueo().isBloqueado() == true).collect(Collectors.toList()).get(0);
		this.bloqueoService.usuarioBloqueadoMotivo(bloqueado.getUser().getUsername());
		assertThat(this.bloqueoService.usuarioBloqueadoMotivo(bloqueado.getUser().getUsername())).isEqualTo(bloqueado.getBloqueo().getDescripcion());	
	}
	
}
