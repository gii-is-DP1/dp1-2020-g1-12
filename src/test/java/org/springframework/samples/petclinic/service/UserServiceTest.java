package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void shouldFindUser() {
		User optperfil = this.userService.findUser("cliente1");
		assertEquals(optperfil.getPassword(), "cliente1");
	}

	@Test
	@Transactional
	public void shouldSaveUser() {
		User u = new User();
		u.setUsername("user");
		u.setPassword("cont");
		this.userService.saveUser(u);
		User user = this.userService.findUser("user");
		assertEquals(u, user);
	}

}
