package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
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
	void shouldInsertVendedor() {
		Vendedor vend = new Vendedor();
		vend.setDni("12345678");
		vend.setNombre("Quique");
		vend.setApellido("Salazar Márquez");
		vend.setDireccion("C/Cuna,1");
		vend.setTelefono("647896370");
		vend.setEmail("quique@mail.com");
		User user = new User();
		user.setUsername("quique");
		user.setPassword("supersecretpassword");
		vend.setUser(user);
		try {
			this.vendedorService.registroVendedor(vend);
		} catch (UsernameDuplicadoException e) {
		}
		Vendedor vendedor = this.vendedorService.findSellerByDni("12345678");
		System.out.println(vendedor);
		assertThat(vend.getUser().getUsername()).isEqualTo(vendedor.getUser().getUsername());
	}

	@Test
	@Transactional
	void shouldUpdateVendedor() throws Exception{
		Vendedor vend = this.vendedorService.findSellerById(1);
		String oldLastName = vend.getApellido();
		String newLastName = oldLastName + "X";

		vend.setApellido(newLastName);
			try {
				this.vendedorService.editar(vend, 1);
			} catch (ContrasenyaNecesariaException e) {
				
			}catch (ContrasenyaNoCoincideException e) {
				
			}

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
