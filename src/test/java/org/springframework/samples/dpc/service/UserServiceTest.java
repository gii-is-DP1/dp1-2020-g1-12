package org.springframework.samples.dpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
    @DisplayName("Test Encontrar usuario")
	void shouldFindUser() {
		User optperfil = this.userService.findUser("cliente1");
		assertEquals("$2a$10$QSvCoF1/GTt6J3zsnBfj2ull8tjIjtYqDt/8QfmskilpIlrJz2Mse", optperfil.getPassword());
	}

	@Test
	@DisplayName("Test Guardar usuario")
	@Transactional
	void shouldSaveUser() {
		User u = new User();
		u.setUsername("user");
		u.setPassword("cont");
		this.userService.saveUser(u);
		User user = this.userService.findUser("user");
		assertEquals(u, user);
	}

}
