package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.AuthoritiesService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.UserService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ClienteController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ClienteControllerTest {

	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_CLIENTE_ID = 4;

	@MockBean
	private VendedorService vendedorService;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private ArticuloService articuloService;

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
		c.setVersion(1);
		c.setDni("56789876");
		c.setNombre("Quique");
		c.setApellido("Salazar");
		c.setDireccion("C/Calle");
		c.setTelefono("615067389");
		c.setEmail("mail@mail.com");
		c.setBloqueo(bw);
		List<Cliente> lc = new ArrayList<>();
		lc.add(c);
		List<Vendedor> lv = new ArrayList<>();
		lv.add(vend);

		given(this.vendedorService.findAllSeller(0, 10, "nombre"))
				.willReturn(new PageImpl<>(lv, PageRequest.of(0, 10, Sort.by(Order.desc("nombre"))), 10));
		given(this.clienteService.findAllClient(0, 10, "nombre"))
				.willReturn(new PageImpl<>(lc, PageRequest.of(0, 10, Sort.by(Order.desc("nombre"))), 10));
		given(this.articuloService.obtenerFiltros(0, 10, "nombre", "clientes")).willReturn(PageRequest.of(0, 10));
		given(this.vendedorService.findSellerById(TEST_VENDEDOR_ID)).willReturn(vend);
		given(this.clienteService.findClientById(TEST_CLIENTE_ID)).willReturn(c);
		given(this.clienteService.getClienteDeSesion()).willReturn(c);

	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Obtener listado de clientes")
	void testProcesadoListadoClientes() throws Exception {
		mockMvc.perform(get("/clientes")).andExpect(status().isOk())
				.andExpect(model().attributeExists("clientes", "vendedores", "ordenacionCliente", "ordenacionVendedor"))
				.andExpect(view().name("moderadores/listadoClientes"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Ver perfil de cliente")
	void testPerfil() throws Exception {
		mockMvc.perform(get("/clientes/perfil")).andExpect(status().isOk()).andExpect(view().name("clientes/perfil"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario editar perfil de cliente")
    void testEdit() throws Exception {
		mockMvc.perform(get("/clientes/editar")).andExpect(status().is2xxSuccessful())
		.andExpect(view().name("clientes/editarPerfil"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Editar perfil de cliente")
	void testProcesoEditarPerfil() throws Exception {
		mockMvc.perform(post("/clientes/editar").param("id", "4").param("version", "1").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").param("version", "1").with(csrf())).andExpect(status().is3xxRedirection());
	}
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Formulario editar perfil de cliente (con error en dni)")
	void testProcesoEditarPerfilErrores() throws Exception {
		mockMvc.perform(post("/clientes/editar").param("id", "4").param("version", "1").param("dni", "").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
					.param("email", "mail@mail.com").param("version", "1").with(csrf())).andExpect(status().is2xxSuccessful())
						.andExpect(view().name("clientes/editarPerfil"));
	}
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Editar perfil de cliente (con error en versión)")
	void testProcesoEditarPerfilErroresVersiones() throws Exception {
		mockMvc.perform(post("/clientes/editar").param("id", "4").param("version", "2").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
					.param("email", "mail@mail.com").param("version", "1").with(csrf())).andExpect(status().is2xxSuccessful())
						.andExpect(view().name("clientes/editarPerfil"));
	}
}
