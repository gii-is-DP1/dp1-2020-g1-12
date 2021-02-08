package org.springframework.samples.dpc.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Authorities;
import org.springframework.samples.dpc.model.Moderador;
import org.springframework.samples.dpc.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ModeradorServiceTest {
	
	@Autowired
	private ModeradorService moderadorService;
	
	@Test
	void testDatosPerfil() {
		Moderador moderador = this.moderadorService.findModeradorById(1);
		assertThat(moderador.getNombre()).isEqualTo("Pedro");
	}
	
	@Test
	@DisplayName("Test Editar un moderador")
	void testSave() {
		Moderador mod = new Moderador();
		User user = new User();
		user.setUsername("moderador10");
		user.setPassword("moderador10");
		user.setEnabled(true);
		
		Authorities a = new Authorities();
		a.setUser(user);
		a.setAuthority("moderador");
		a.setId(1);
		
		mod.setUser(user);
		mod.setNombre("Pepe");
		mod.setApellido("López");
		mod.setDireccion("c/Real, 10");
		mod.setDni("12345678");
		mod.setTelefono("610111214");
		this.moderadorService.guardar(mod);
		Integer id = mod.getId();
		Moderador moderador = this.moderadorService.findModeradorById(id);
		assertThat(mod).isEqualTo(moderador);
	}

}
