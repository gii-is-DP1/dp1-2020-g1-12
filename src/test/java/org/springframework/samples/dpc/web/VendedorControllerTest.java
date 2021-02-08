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
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.AuthoritiesService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.LineaPedidoService;
import org.springframework.samples.dpc.service.MensajeService;
import org.springframework.samples.dpc.service.SolicitudService;
import org.springframework.samples.dpc.service.UserService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = VendedorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class VendedorControllerTest {

	private static final int TEST_VENDEDOR_ID = 2;
	private static final int TEST_CLIENTE_ID = 1;
	private static final int TEST_ARTICULO_ID = 1;
	private static final int TEST_ARTICULO_FALLO_ID = 100;
	private static final int TEST_SOLICITUD_ID = 2;
	private static final int TEST_SOLICITUD_FALLO_ID = 100;

	@MockBean
	private VendedorService vendedorService;
	
	@MockBean
	private ArticuloService articuloService;
	
	@MockBean
	private ClienteService clienteService;

	@MockBean
	private SolicitudService solicitudService;
	
	@MockBean
	private LineaPedidoService lineaPedidoService;
	
	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private MensajeService mensajeService;
	
	@Autowired
	private MockMvc mockMvc;

	private Vendedor vend;
	private Articulo art;
	private Cliente c;
	private Solicitud sol;

	@BeforeEach
	void setup() {

		vend = new Vendedor();
		vend.setId(TEST_VENDEDOR_ID);
		vend.setVersion(1);
		vend.setDni("56789876");
		vend.setNombre("Quique");
		vend.setApellido("Salazar");
		vend.setDireccion("C/Calle");
		vend.setTelefono("615067389");
		vend.setEmail("mail@mail.com");
		vend.setVersion(1);
		Bloqueo b = new Bloqueo();
		b.setId(1);
		b.setBloqueado(false);
		b.setDescripcion("");
		vend.setBloqueo(b);
		Oferta o = new Oferta();
		o.setVersion(1);
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
		sol = new Solicitud();
		sol.setId(TEST_SOLICITUD_ID);
		sol.setDescripcion("Solicitud de venta de MSI Prestige 14 Evo A11M-003ES");
		sol.setGastoEnvio(5.0);
		sol.setMarca("MSI Prestige");
		sol.setModelo("14 Evo A11M-003ES");
		sol.setPrecio(988.99);
		sol.setRespuesta("");
		sol.setSituacion(Situacion.Pendiente);
		sol.setStock(5);
		sol.setTiempoEntrega(8);
		sol.setTipo(Tipo.Nuevo);
		sol.setUrlImagen("vacia");
		sol.setVendedor(vend);
		List<Solicitud> ls = new ArrayList<>();
		ls.add(sol);
		List<Articulo> la = new ArrayList<>();
		la.add(art);
		
		given(this.articuloService.articulosEnVentaByProvider(TEST_VENDEDOR_ID, 0, 10, "-id")).
		willReturn(new PageImpl<>(la, PageRequest.of(0, 10, Sort.by(Order.desc("id"))), 10));
		given(this.solicitudService.getsolicitudesByProvider(TEST_VENDEDOR_ID, 0, 10, "-id")).
		willReturn(new PageImpl<>(ls, PageRequest.of(0, 10, Sort.by(Order.desc("id"))), 10));
		given(this.solicitudService.detallesSolicitud(TEST_SOLICITUD_ID)).willReturn(sol);
		given(this.vendedorService.findSellerById(TEST_VENDEDOR_ID)).willReturn(vend);
		given(this.vendedorService.getVendedorDeSesion()).willReturn(vend);
		given(this.vendedorService.obtenerIdSesion()).willReturn(TEST_VENDEDOR_ID);
		given(this.vendedorService.getVendedorDeSesion()).willReturn(vend);		
		given(this.vendedorService.vendedorDeUnArticulo(TEST_ARTICULO_ID)).willReturn(vend);
		given(this.vendedorService.vendedorDeUnArticulo(TEST_ARTICULO_FALLO_ID)).willReturn(null);				
		given(this.articuloService.obtenerFiltros(0, 10, "-id", "articulo")).willReturn(PageRequest.of(0, 10));
		given(this.articuloService.findArticuloById(TEST_ARTICULO_ID)).willReturn(art);
		given(this.clienteService.findClientById(TEST_CLIENTE_ID)).willReturn(c);
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar perfil de vendedor")
	void testPerfil() throws Exception {
		mockMvc.perform(get("/vendedores/perfil")).andExpect(status().isOk())
				.andExpect(model().attributeExists("vendedor")).andExpect(view().name("vendedores/perfil"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario editar perfil de vendedor")
    void testEdit() throws Exception {
		mockMvc.perform(get("/vendedores/editar")).andExpect(status().is2xxSuccessful())
		.andExpect(view().name("vendedores/editarPerfil"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Editar perfil de vendedor")
	void testProcesoEditarPerfiln() throws Exception {
		mockMvc.perform(post("/vendedores/editar").param("id", "2").param("version", "1").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").with(csrf())).andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Editar perfil de vendedor (con error en dni)")
	void testProcesoEditarPerfilConErrores() throws Exception {
		mockMvc.perform(post("/vendedores/editar").param("id", "2").param("version", "1").param("dni", "").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").with(csrf())).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("vendedores/editarPerfil"));
	}
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Editar perfil de vendedor (con error en versión)")
	void testProcesoEditarPerfilErroresVersiones() throws Exception {
		mockMvc.perform(post("/vendedores/editar").param("id", "2").param("version", "2").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
					.param("email", "mail@mail.com").with(csrf())).andExpect(status().is2xxSuccessful())
						.andExpect(view().name("vendedores/editarPerfil"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Ver perfil de cliente desde vendedor")
	void testProcesoPerfilCliente() throws Exception {
		mockMvc.perform(get("/vendedores/perfilCliente/" + TEST_CLIENTE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("cliente")).andExpect(view().name("vendedores/perfilCliente"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar articulos en venta")
	void testProcesoArticulosVenta() throws Exception {
		mockMvc.perform(get("/vendedores/articulosEnVenta")).andExpect(status().isOk())
				.andExpect(model().attributeExists("articulos")).andExpect(view().name("vendedores/listadoArticulos"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar listado de solicitudes")
	void testMostrarListadoSolicitudes() throws Exception {
		mockMvc.perform(get("/vendedores/listadoSolicitudes")).andExpect(status().isOk())
				.andExpect(model().attributeExists("solicitudes")).andExpect(view().name("vendedores/listadoSolicitudes"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar detalles de artículo")
	void testMostrarArticuloDetallado() throws Exception {
		mockMvc.perform(get("/vendedores/articulo/" + TEST_ARTICULO_ID)).andExpect(status().is2xxSuccessful())
					.andExpect(view().name("vendedores/articulo"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar detalles de artículo ")
	void testMostrarArticuloDetalladoConErrores() throws Exception {
		mockMvc.perform(get("/vendedores/articulo/" + TEST_ARTICULO_FALLO_ID)).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/vendedores/articulosEnVenta"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Mostrar solicitud detallada")
	void testMostrarSolicitudDetallada() throws Exception {
		mockMvc.perform(get("/vendedores/solicitud/" + TEST_SOLICITUD_ID)).andExpect(status().is2xxSuccessful())
					.andExpect(view().name("vendedores/solicitud"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Mostrar solicitud detallada (con error en id)")
	void testMostrarSolicitudDetalladaConErrores() throws Exception {
		mockMvc.perform(get("/vendedores/solicitud/" + TEST_SOLICITUD_FALLO_ID)).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/vendedores/listadoSolicitudes"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Eliminar artículo en venta")
	void testEliminarArticulo() throws Exception {
		mockMvc.perform(get("/vendedores/eliminarArticulo/" + TEST_ARTICULO_ID)).andExpect(status()
				.is3xxRedirection()).andExpect(view().name("redirect:/vendedores/articulosEnVenta"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Eliminar solicitud pendiente")
	void testEliminarSolicitud() throws Exception {
		mockMvc.perform(get("/vendedores/eliminarSolicitud/" + TEST_SOLICITUD_ID)).andExpect(status()
				.is3xxRedirection()).andExpect(view().name("redirect:/vendedores/listadoSolicitudes"));
	}
}
