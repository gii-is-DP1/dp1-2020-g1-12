package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VendedorServiceTest {

	public final Integer ARTICULO_ID = 1;

	@Autowired
	private VendedorService vendedorService;

	@Test
	void shouldFindVendedorById() {
		Vendedor optperfil = this.vendedorService.findSellerById(1);
		assertEquals("Pepe", optperfil.getNombre());
	}

	@Test
	void shouldFindVendedorByDni() {
		Vendedor optperfil = this.vendedorService.findSellerByDni("29976789");
		assertEquals("Pepe", optperfil.getNombre());
	}

	@Test
	@Transactional
	void shouldInsertVendedor() {
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
	void shouldUpdateVendedor() {
		Vendedor vend = this.vendedorService.findSellerById(1);
		String oldLastName = vend.getApellido();
		String newLastName = oldLastName + "X";

		vend.setApellido(newLastName);
		this.vendedorService.guardar(vend);

		vend = this.vendedorService.findSellerById(1);
		assertThat(vend.getApellido()).isEqualTo(newLastName);
	}

	@Test
	void testVendedorArticulo() {
		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(ARTICULO_ID);
		Vendedor vendedor2 = vendedorService.vendedorDeUnArticulo(100);
		assertThat(vendedor.getId()).isEqualTo(1);
		assertThat(vendedor.getApellido()).isEqualTo("Lorca");
		assertThat(vendedor2).isNull();
		assertNotEquals("2", vendedor.getId());
	}

}
