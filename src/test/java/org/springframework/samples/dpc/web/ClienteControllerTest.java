package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.AuthoritiesService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.UserService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ClienteController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ClienteControllerTest {

	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_CLIENTE_ID = 4;

	@MockBean
	private VendedorService vendedorService;
	@MockBean
	private ClienteService clienteService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Vendedor vend;
	private Cliente c;

	@BeforeEach
	void setup() {

		vend = new Vendedor();
		vend.setId(TEST_VENDEDOR_ID);
		vend.setDni("56789876");
		vend.setNombre("Quique");
		vend.setApellido("Salazar");
		vend.setDireccion("C/Calle");
		vend.setTelefono("615067389");
		vend.setEmail("mail@mail.com");
		Bloqueo b = new Bloqueo();
		b.setId(1);
		b.setBloqueado(false);
		b.setDescripcion("");
		vend.setBloqueo(b);
		Bloqueo bw = new Bloqueo();
		bw.setId(4);
		bw.setBloqueado(false);
		bw.setDescripcion("");
		c = new Cliente();
		c.setId(TEST_CLIENTE_ID);
		c.setDni("56789876");
		c.setNombre("Quique");
		c.setApellido("Salazar");
		c.setDireccion("C/Calle");
		c.setTelefono("615067389");
		c.setEmail("mail@mail.com");
		c.setBloqueo(bw);

		given(this.vendedorService.findSellerById(TEST_VENDEDOR_ID)).willReturn(vend);
		given(this.clienteService.findClientById(TEST_CLIENTE_ID)).willReturn(c);
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcesadoListadoClientes() throws Exception {
		mockMvc.perform(get("/clientes")).andExpect(status().isOk())
				.andExpect(model().attributeExists("clientes", "vendedores"))
				.andExpect(view().name("moderadores/listadoClientes"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/clientes/perfil")).andExpect(status().isOk()).andExpect(view().name("clientes/perfil"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreacion() throws Exception {
		mockMvc.perform(post("/clientes/editar").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").with(csrf())).andExpect(status().is3xxRedirection());
	}

}
