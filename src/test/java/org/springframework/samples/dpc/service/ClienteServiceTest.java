package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.UsernameDuplicadoException;
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
		User user = new User();
		user.setUsername("quique");
		user.setPassword("supersecretpassword");
		c.setUser(user);
		try {
			this.clienteService.registroCliente(c);
		} catch (UsernameDuplicadoException e) {
		}
		
		Cliente cliente = this.clienteService.findClientByDni("12345678");
		assertThat(c.getUser().getUsername()).isEqualTo(cliente.getUser().getUsername());
	}

	@Test
	@Transactional
	void shouldUpdatecliente() throws Exception {
		Cliente c = this.clienteService.findClientById(1);
		String oldLastName = c.getApellido();
		String newLastName = oldLastName + "X";

		c.setApellido(newLastName);
		try {
			this.clienteService.editar(c,1);
		} catch (ContrasenyaNecesariaException e) {
			
		}catch (ContrasenyaNoCoincideException e) {
			
		}
		c = this.clienteService.findClientById(1);
		assertThat(c.getApellido()).isEqualTo(newLastName);
	}

}
