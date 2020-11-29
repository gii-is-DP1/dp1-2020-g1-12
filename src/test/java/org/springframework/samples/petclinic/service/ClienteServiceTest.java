package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;

	@Test
	public void testCountWithInitialData() {
		int count = clienteService.clienteCount();
		assertEquals(count, 1);
	}

	@Test
	public void shouldFindclienteById() {
		Cliente optperfil = this.clienteService.findClientById(1);
		assertEquals(optperfil.getNombre(), "Pepe");
	}

	@Test
	public void shouldFindclienteByDni() {
		Cliente optperfil = this.clienteService.findClientByDni("23456789");
		assertEquals(optperfil.getNombre(), "Juan");
	}

	@Test
	@Transactional
	public void shouldInsertcliente() {
		Cliente c = new Cliente();
		c.setDni("12345678");
		c.setNombre("Quique");
		c.setApellido("Salazar Márquez");
		c.setDireccion("C/Cuna,1");
		c.setTelefono("647896370");
		c.setEmail("quique@mail.com");
		Bloqueo b = new Bloqueo();
		b.setId(4);
		b.setBloqueado(false);
		b.setDescripcion("");
		c.setBloqueo(b);
		User user = new User();
		user.setUsername("quique");
		user.setPassword("supersecretpassword");
		user.setEnabled(true);
		c.setUser(user);
		this.clienteService.guardar(c);
		Cliente cliente = this.clienteService.findClientByDni("12345678");
		assertEquals(c, cliente);
	}

	@Test
	@Transactional
	public void shouldUpdatecliente() {
		Cliente c = this.clienteService.findClientById(1);
		String oldLastName = c.getApellido();
		String newLastName = oldLastName + "X";

		c.setApellido(newLastName);
		this.clienteService.guardar(c);

		c = this.clienteService.findClientById(1);
		assertThat(c.getApellido()).isEqualTo(newLastName);
	}

}
