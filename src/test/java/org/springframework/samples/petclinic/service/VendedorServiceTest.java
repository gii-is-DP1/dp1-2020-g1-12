package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.model.Situacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tipo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VendedorServiceTest {

	@Autowired
	private VendedorService vendedorService;

	@Test
	public void shouldFindVendedorById() {
		Vendedor optperfil = this.vendedorService.findSellerById(1);
		assertEquals(optperfil.getNombre(), "Pepe");
	}

	@Test
	public void shouldFindVendedorByDni() {
		Vendedor optperfil = this.vendedorService.findSellerByDni("29976789");
		assertEquals(optperfil.getNombre(), "Pepe");
	}

	@Test
	@Transactional
	public void shouldInsertVendedor() {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		Bloqueo b = new Bloqueo();
		b.setId(1);
		b.setBloqueado(false);
		b.setDescripcion("");
		vend.setBloqueo(b);
		User user = new User();
		user.setUsername("quique");
		user.setPassword("supersecretpassword");
		user.setEnabled(true);
		vend.setUser(user);
		this.vendedorService.guardar(vend);
		Vendedor vendedor = this.vendedorService.findSellerByDni("12345678");
		assertEquals(vend, vendedor);
	}

	@Test
	@Transactional
	public void shouldUpdateVendedor() {
		Vendedor vend = this.vendedorService.findSellerById(1);
		String oldLastName = vend.getApellido();
		String newLastName = oldLastName + "X";

		vend.setApellido(newLastName);
		this.vendedorService.guardar(vend);

		vend = this.vendedorService.findSellerById(1);
		assertThat(vend.getApellido()).isEqualTo(newLastName);
	}

//	@Test
//	public void shouldFindCliente() {
//
//	}
}
