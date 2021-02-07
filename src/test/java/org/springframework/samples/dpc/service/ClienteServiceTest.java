package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoValidaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaParecidaUsuarioException;
import org.springframework.samples.dpc.service.exceptions.UsernameDuplicadoException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;

	@Test
	void shouldFindclienteById() {
		Cliente optperfil = this.clienteService.findClientById(1);
		assertThat(optperfil.getNombre()).isEqualTo("Juan");
		assertThat(optperfil.getUser().getPassword().matches("cliente1"));
	}

	@Test
	void shouldFindclienteByDni() {
		Cliente optperfil = this.clienteService.findClientByDni("23456789");
		assertThat(optperfil.getNombre()).isEqualTo("Juan");
	}

	@Test
	void shouldInsertcliente() throws Exception {
		Cliente c = new Cliente();
		c.setDni("12345678");
		c.setNombre("Quique");
		c.setApellido("Salazar Márquez");
		c.setDireccion("C/Cuna,1");
		c.setTelefono("647896370");
		c.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("Supersecret1");
		user.setNewPassword("Supersecret1");
		c.setUser(user);
		this.clienteService.registroCliente(c);
		Cliente cliente = this.clienteService.findClientByDni("12345678");
		assertThat(c.getUser().getUsername()).isEqualTo(cliente.getUser().getUsername());
	}
	
	@Test
	void shouldInsertclienteConErroresContrasenya() throws ContrasenyaNoValidaException {
		Cliente c = new Cliente();
		c.setDni("12345678");
		c.setNombre("Quique");
		c.setApellido("Salazar Márquez");
		c.setDireccion("C/Cuna,1");
		c.setTelefono("647896370");
		c.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("asdf");
		user.setNewPassword("asdf");
		c.setUser(user);
		assertThrows(ContrasenyaNoValidaException.class, () -> this.clienteService.registroCliente(c));
	}

	@Test
	void shouldInsertclienteConErroresUsername() throws UsernameDuplicadoException {
		Cliente c = new Cliente();
		c.setDni("12345678");
		c.setNombre("Quique");
		c.setApellido("Salazar Márquez");
		c.setDireccion("C/Cuna,1");
		c.setTelefono("647896370");
		c.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("cliente1");
		user.setPassword("Supersecret1");
		user.setNewPassword("Supersecret1");
		c.setUser(user);
		assertThrows(UsernameDuplicadoException.class, () -> this.clienteService.registroCliente(c));
	}
	
	@Test
	void shouldInsertclienteConErroresContrasenya2() throws ContrasenyaNoCoincideException {
		Cliente c = new Cliente();
		c.setDni("12345678");
		c.setNombre("Quique");
		c.setApellido("Salazar Márquez");
		c.setDireccion("C/Cuna,1");
		c.setTelefono("647896370");
		c.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("Supersecret1");
		user.setNewPassword("Super1");
		c.setUser(user);
		assertThrows(ContrasenyaNoCoincideException.class, () -> this.clienteService.registroCliente(c));
	}
	
	@Test
	void shouldInsertclienteConErroresContrasenya3() throws ContrasenyaParecidaUsuarioException {
		Cliente c = new Cliente();
		c.setDni("12345678");
		c.setNombre("Quique");
		c.setApellido("Salazar Márquez");
		c.setDireccion("C/Cuna,1");
		c.setTelefono("647896370");
		c.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("Quique10");
		user.setPassword("Quique10");
		user.setNewPassword("Quique10");
		c.setUser(user);
		assertThrows(ContrasenyaParecidaUsuarioException.class, () -> this.clienteService.registroCliente(c));
	}
	
	
//	@Test
//	void shouldUpdatecliente() throws Exception {
//		Cliente c = new Cliente();
//		c = this.clienteService.findClientById(1);
//		User user = c.getUser();
//		String oldLastName = c.getApellido();
//		String newLastName = oldLastName + "X";
//
//		c.setApellido(newLastName);
//		System.out.println(c.getUser().getPassword() + "+++++++++++++++++++++++++++++++++++++++");
//
//		user.setPassword("cliente1");
//		c.getUser().setNewPassword("Pepito84");
//		System.out.println(this.clienteService.findClientById(1).getUser().getPassword() + "+++++++++++++++++++++++++++++++++++++++");
//		this.clienteService.editar(c,1);
//		c = this.clienteService.findClientById(1);
//		assertThat(c.getApellido()).isEqualTo(newLastName);
//	}

}
