package org.springframework.samples.dpc.service;


import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat("Pedro").isEqualTo(moderador.getNombre());
	}
	
	@Test
	void testEditar() {
		Moderador mod = this.moderadorService.findModeradorById(1);
		
		String newDni = "12345678C";
		mod.setDni(newDni);
		this.moderadorService.guardar(mod);
		
		mod = this.moderadorService.findModeradorById(1);
		assertThat(mod.getDni()).isEqualTo(newDni);
	}
	
	@Test
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
