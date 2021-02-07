package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void shouldFindVendedorById() {
		Vendedor optperfil = this.vendedorService.findSellerById(1);
		assertThat("Pepe").isEqualTo(optperfil.getNombre());
	}

	@Test
	void shouldFindVendedorByDni() {
		Vendedor optperfil = this.vendedorService.findSellerByDni("29976789");
		assertThat("Pepe").isEqualTo(optperfil.getNombre());
	}

	@Test
	@Transactional
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
	
//	@Test
//	@Transactional
//	void shouldUpdateVendedor() throws Exception{
//		Vendedor vend = this.vendedorService.findSellerById(1);
//		String oldLastName = vend.getApellido();
//		String newLastName = oldLastName + "X";
//
//		vend.setApellido(newLastName);
//		vend.getUser().setPassword("vendedor1");
//		vend.getUser().setNewPassword("Vendedor10");
//		this.vendedorService.editar(vend, 1);
//		assertThat(vend.getApellido()).isEqualTo(newLastName);
//	}

	@Test
	void testVendedorArticulo() {
		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(ARTICULO_ID);
		Vendedor vendedor2 = vendedorService.vendedorDeUnArticulo(100);
		assertThat(vendedor.getId()).isEqualTo(1);
		assertThat(vendedor.getApellido()).isEqualTo("Lorca");
		assertThat(vendedor2).isNull();
		assertNotEquals("2", vendedor.getId().toString());
	}
	
	@Test
	void testListadoVendedores() {
		Page<Vendedor> listado = this.vendedorService.findAllSeller(0, 5, "nombre");
		assertThat(listado.getSize()).isNotZero();
	}
	
	@Test
	void testObtenerBloqueo() {
		Vendedor vendedor = this.vendedorService.findSellerById(1);
		Bloqueo bloqueo = this.vendedorService.getBloqueoVendedor(vendedor.getUser().getUsername());
		assertThat(bloqueo.isBloqueado()).isFalse();
	}
}