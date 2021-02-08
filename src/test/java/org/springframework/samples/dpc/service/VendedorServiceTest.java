package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoValidaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaParecidaUsuarioException;
import org.springframework.samples.dpc.service.exceptions.UsernameDuplicadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VendedorServiceTest {

	public final Integer ARTICULO_ID = 1;

	@Autowired
	private VendedorService vendedorService;

	@Test
	@DisplayName("Test Obtener Vendedor por Id")
	void shouldFindVendedorById() {
		Vendedor optperfil = this.vendedorService.findSellerById(1);
		assertThat("Pepe").isEqualTo(optperfil.getNombre());
	}

	@Test
	@DisplayName("Test Obtener Vendedor por dni")
	void shouldFindVendedorByDni() {
		Vendedor optperfil = this.vendedorService.findSellerByDni("29976789");
		assertThat("Pepe").isEqualTo(optperfil.getNombre());
	}

	@Test
	@Transactional
	@DisplayName("Test Regsitrar vendedor")
	void shouldInsertVendedor() throws Exception {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("Supersecret1");
		user.setNewPassword("Supersecret1");
		vend.setUser(user);
		this.vendedorService.registroVendedor(vend);
		Vendedor vendedor = this.vendedorService.findSellerByDni("12345678");
		assertThat(vend.getUser().getUsername()).isEqualTo(vendedor.getUser().getUsername());
	}
	
	@Test
	@Transactional
	@DisplayName("Test Regsitrar vendedor con error en usuario")
	void shouldInsertVendedorConErroresUsername() throws UsernameDuplicadoException {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("vendedor1");
		user.setPassword("Supersecret1");
		user.setNewPassword("Supersecret1");
		vend.setUser(user);
		assertThrows(UsernameDuplicadoException.class, () -> this.vendedorService.registroVendedor(vend));
	}

	@Test
	@Transactional
	@DisplayName("Test Regsitrar vendedor con error en contraseña")
	void shouldInsertVendedorConErroresContrasenya() throws ContrasenyaNoValidaException {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("asdf");
		user.setNewPassword("asdf");
		vend.setUser(user);
		assertThrows(ContrasenyaNoValidaException.class, () -> this.vendedorService.registroVendedor(vend));
	}
	
	@Test
	@Transactional
	@DisplayName("Test Regsitrar vendedor con error en contraseña2")
	void shouldInsertVendedorConErroresContrasenya2() throws ContrasenyaNoCoincideException {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("asdf10ASDF");
		user.setNewPassword("asdf10asdF");
		vend.setUser(user);
		assertThrows(ContrasenyaNoCoincideException.class, () -> this.vendedorService.registroVendedor(vend));
	}
	
	@Test
	@Transactional
	@DisplayName("Test Regsitrar vendedor con error en contraseña3")
	void shouldInsertVendedorConErroresContrasenya3() throws ContrasenyaParecidaUsuarioException {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("Quique10");
		user.setPassword("Quique10");
		user.setNewPassword("Quique10");
		vend.setUser(user);
		assertThrows(ContrasenyaParecidaUsuarioException.class, () -> this.vendedorService.registroVendedor(vend));
	}
	
	@Test
	@DisplayName("Test Obtener vendedor de un artículo")
	void testVendedorArticulo() {
		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(ARTICULO_ID);
		Vendedor vendedor2 = vendedorService.vendedorDeUnArticulo(100);
		assertThat(vendedor.getId()).isEqualTo(1);
		assertThat(vendedor.getApellido()).isEqualTo("Lorca");
		assertThat(vendedor2).isNull();
		assertNotEquals("2", vendedor.getId().toString());
	}
	
	@Test
	@DisplayName("Test Listado de vendedores")
	void testListadoVendedores() {
		Page<Vendedor> listado = this.vendedorService.findAllSeller(0, 5, "nombre");
		assertThat(listado.getSize()).isNotZero();
	}
	
	@Test
	@DisplayName("Test Obtener bloqueo de vendedor")
	void testObtenerBloqueo() {
		Vendedor vendedor = this.vendedorService.findSellerById(1);
		Bloqueo bloqueo = this.vendedorService.getBloqueoVendedor(vendedor.getUser().getUsername());
		assertThat(bloqueo.isBloqueado()).isFalse();
	}
}