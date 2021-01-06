package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;

	@Test
	void shouldFindclienteById() {
		Cliente optperfil = this.clienteService.findClientById(1);
		assertThat(optperfil.getNombre()).isEqualTo("Juan");
	}

	@Test
	void shouldFindclienteByDni() {
		Cliente optperfil = this.clienteService.findClientByDni("23456789");
		assertThat(optperfil.getNombre()).isEqualTo("Juan");
	}

	@Test
	void shouldInsertcliente() {
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
		Cesta cesta = new Cesta();
		cesta.setId(c.getId());
		List<LineaCesta> lineaCesta = new ArrayList<>();
		cesta.setLineas(lineaCesta);
		c.setCesta(cesta);
		this.clienteService.guardar(c);
		Cliente cliente = this.clienteService.findClientByDni("12345678");
		assertThat(c).isEqualTo(cliente);
	}

	@Test
	@Transactional
	void shouldUpdatecliente() {
		Cliente c = this.clienteService.findClientById(1);
		String oldLastName = c.getApellido();
		String newLastName = oldLastName + "X";

		c.setApellido(newLastName);
		this.clienteService.guardar(c);

		c = this.clienteService.findClientById(1);
		assertThat(c.getApellido()).isEqualTo(newLastName);
	}

}
