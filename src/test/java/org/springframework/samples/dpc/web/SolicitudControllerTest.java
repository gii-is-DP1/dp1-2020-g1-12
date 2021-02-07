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
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.SolicitudService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value=SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class SolicitudControllerTest {
	
	private static final int TEST_SOLICITUD_ID = 1;
	private static final int TEST_VENDEDOR_ID = 1;

	@MockBean
	private VendedorService vendedorService;
        
    @MockBean
	private ArticuloService articuloService;
    
    @MockBean
	private SolicitudService solicitudService;

	@Autowired
	private MockMvc mockMvc;
	
	
	private Solicitud solicitud;
	
	@BeforeEach
	void setup() {

		solicitud = new Solicitud();
		solicitud.setId(TEST_SOLICITUD_ID);
		solicitud.setDescripcion("Solicitud de venta de MSI Prestige 14 Evo A11M-003ES");
		solicitud.setGastoEnvio(5.0);
		solicitud.setMarca("MSI Prestige");
		solicitud.setModelo("14 Evo A11M-003ES");
		solicitud.setPrecio(988.99);
		solicitud.setRespuesta("");
		solicitud.setVersion(1);
		solicitud.setSituacion(Situacion.Aceptada);
		solicitud.setStock(5);
		solicitud.setTiempoEntrega(8);
		solicitud.setTipo(Tipo.Nuevo);
		solicitud.setUrlImagen("vacia");
		List<Solicitud> l = new ArrayList<>();
		l.add(solicitud);
		
		given(this.solicitudService.solicitudesPendientes(0, 10, "-id")).
		willReturn(new PageImpl<>(l, PageRequest.of(0, 10, Sort.by(Order.desc("id"))), 10));
		given(this.articuloService.obtenerFiltros(0, 10, "-id", "articulo")).willReturn(PageRequest.of(0, 10));
		given(this.solicitudService.detallesSolicitud(TEST_SOLICITUD_ID)).willReturn(solicitud);
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario de crear solicitud")
    void testCreacionFormularioSolicitud() throws Exception {
		mockMvc.perform(get("/solicitudes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitud"))
		.andExpect(view().name("solicitudes/editarSolicitud"));
	}

	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear solicitud")
    void testCreacion() throws Exception {
	mockMvc.perform(post("/solicitudes/save").param("descripcion", "Laptop ultima generación").param("modelo", "14 Evo A11M-003ES")
						.param("marca", "MSI-Persisten")
						.param("urlImagen", "https://imagen.png")
						.param("precio", "949.99")
						.param("stock", "30")
						.param("tiempoEntrega", "3")
						.param("gastoEnvio", "7")
						.param("tipo","Nuevo").with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/vendedores/listadoSolicitudes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear solicitud (con error en URL)")
    void testCreacionConErrores() throws Exception {
	mockMvc.perform(post("/solicitudes/save").param("descripcion", "Laptop ultima generación").param("modelo", "14 Evo A11M-003ES")
						.param("marca", "MSI-Persisten")
						.param("urlImagen", "vacia")
						.param("precio", "949.99")
						.param("stock", "30")
						.param("tiempoEntrega", "3")
						.param("gastoEnvio", "7")
						.param("tipo","Nuevo").with(csrf()))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/editarSolicitud"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Visualizar listado de solicitudes")
    void testProcesoListadoSolicitud() throws Exception {
		mockMvc.perform(get("/solicitudes")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitudes"))
		.andExpect(view().name("solicitudes/listadoSolicitudes"));
	}

	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Ver detalles de una solicitud")
    void testProcesoSolicitud() throws Exception {
		mockMvc.perform(get("/solicitudes/"+TEST_SOLICITUD_ID)).andExpect(status().isOk())
		.andExpect(view().name("solicitudes/detalles"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Aceptar una solicitud")
    void testProcesoAceptarSolicitud() throws Exception {
		mockMvc.perform(post("/solicitudes/"+TEST_SOLICITUD_ID+"/aceptar").with(csrf()).param("version", "1")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitudes"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/listadoSolicitudes"));
	}
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Aceptar una solicitud (con error de versión)")
    void testProcesoAceptarSolicitudErrorVersion() throws Exception {
		mockMvc.perform(post("/solicitudes/"+TEST_SOLICITUD_ID+"/aceptar").with(csrf()).param("version", "2")).andExpect(status().isOk())
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/detalles"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Denegar una solicitud")
    void testProcesoDenegarSolicitud() throws Exception {
		mockMvc.perform(post("/solicitudes/"+TEST_SOLICITUD_ID+"/denegar").with(csrf()).param("version", "1").param("respuesta", "No se permite la venta de este producto."))
		.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/listadoSolicitudes"));
	}
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Denegar una solicitud (con error de versión)")
    void testProcesoDenegarSolicitudErrorVersion() throws Exception {
		mockMvc.perform(post("/solicitudes/"+TEST_SOLICITUD_ID+"/denegar").with(csrf()).param("version", "2").param("respuesta", "No se permite la venta de este producto."))
		.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/detalles"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Ver perfil del solicitante")
    void testProcesoPerfilSolicitante() throws Exception {
		mockMvc.perform(get("/solicitudes/"+TEST_SOLICITUD_ID+"/solicitante/"+TEST_VENDEDOR_ID))
		.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/solicitante"));
	}
}
