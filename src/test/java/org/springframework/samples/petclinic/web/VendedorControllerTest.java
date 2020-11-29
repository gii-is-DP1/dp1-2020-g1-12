package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Tipo;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = VendedorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class VendedorControllerTest {

	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_CLIENTE_ID = 1;
	private static final int TEST_ARTICULO_ID = 1;

	@MockBean
	private VendedorService vendedorService;
	@MockBean
	private ArticuloService articuloService;
	@MockBean
	private ClienteService clienteService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Vendedor vend;
	private Articulo art;
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
		Oferta o = new Oferta();
		o.setDisponibilidad(true);
		o.setPorcentaje(30);
		art = new Articulo();
		art.setId(TEST_ARTICULO_ID);
		art.setModelo("Vant1");
		art.setMarca("Vant");
		art.setUrlImagen("sdfhjk.com");
		art.setPrecio(800.);
		art.setStock(3);
		art.setTipo(Tipo.Nuevo);
		art.setTiempoEntrega(2);
		art.setGastoEnvio(5.);
		art.setOferta(o);
		c = new Cliente();
		c.setId(TEST_CLIENTE_ID);
		c.setDni("56789876");
		c.setNombre("Quique");
		c.setApellido("Salazar");
		c.setDireccion("C/Calle");
		c.setTelefono("615067389");
		c.setEmail("mail@mail.com");
		c.setBloqueo(b);

		given(this.vendedorService.findSellerById(TEST_VENDEDOR_ID)).willReturn(vend);
		given(this.articuloService.findArticuloById(TEST_ARTICULO_ID)).willReturn(art);
		given(this.clienteService.findClientById(TEST_CLIENTE_ID)).willReturn(c);
	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testInitCreationForm() throws Exception {
//		mockMvc.perform(get("/vendedores/perfil")).andExpect(status().isOk())
//				.andExpect(model().attributeExists("vendedor")).andExpect(view().name("vendedores/perfil"));
//	}

	@WithMockUser(value = "spring")
	@Test
	void testCreacion() throws Exception {
		mockMvc.perform(post("/vendedores/editar").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").with(csrf())).andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcesoPerfilCliente() throws Exception {
		mockMvc.perform(get("/vendedores/perfilCliente/" + TEST_CLIENTE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("cliente")).andExpect(view().name("vendedores/perfilCliente"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcesoArticulosVenta() throws Exception {
		mockMvc.perform(get("/vendedores/articulosEnVenta")).andExpect(status().isOk())
				.andExpect(model().attributeExists("articulos")).andExpect(view().name("vendedores/listadoArticulos"));
	}
}