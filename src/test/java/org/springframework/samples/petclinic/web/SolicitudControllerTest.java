package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Situacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tipo;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class SolicitudControllerTest {
	
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
		solicitud.setSituacion(Situacion.Aceptada);
		solicitud.setStock(5);
		solicitud.setTiempoEntrega(8);
		solicitud.setTipo(Tipo.Nuevo);
		solicitud.setUrlImagen("vacia");
		given(this.solicitudService.findById(TEST_SOLICITUD_ID)).willReturn(solicitud);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCreacionFormularioSolicitud() throws Exception {
		mockMvc.perform(get("/solicitudes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitud"))
		.andExpect(view().name("solicitudes/editarSolicitud"));
	}

	@WithMockUser(value = "spring")
    @Test
    void testCreacion() throws Exception {
	mockMvc.perform(post("/solicitudes/save").param("descripcion", "Laptop ultima generación").param("modelo", "14 Evo A11M-003ES")
						.param("marca", "MSI-Persisten")
						.param("urlImagen", "vacia")
						.param("precio", "949.99")
						.param("stock", "30")
						.param("tiempoEntrega", "3")
						.param("gastoEnvio", "7")
						.param("tipo","Nuevo").with(csrf()))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesoListadoSolicitud() throws Exception {
		mockMvc.perform(get("/solicitudes")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitudes"))
		.andExpect(view().name("solicitudes/listadoSolicitudes"));
	}

//	@WithMockUser(value = "spring")
//    @Test
//    void testProcesoSolicitud() throws Exception {
//		mockMvc.perform(get("/solicitudes/"+TEST_SOLICITUD_ID)).andExpect(status().isOk())
//		.andExpect(view().name("solicitudes/detalles"));
//	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesoAceptarSolicitud() throws Exception {
		mockMvc.perform(get("/solicitudes/"+TEST_SOLICITUD_ID+"/aceptar")).andExpect(status().isOk()).andExpect(model().attributeExists("solicitudes"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/listadoSolicitudes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesoDenegarSolicitud() throws Exception {
		mockMvc.perform(post("/solicitudes/"+TEST_SOLICITUD_ID+"/denegar").param("respuesta", "En esta página no vendemos armas.").with(csrf()))
		.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/listadoSolicitudes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesoPerfilSolicitante() throws Exception {
		mockMvc.perform(get("/solicitudes/solicitante/"+TEST_VENDEDOR_ID))
		.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andExpect(view().name("solicitudes/solicitante"));
	}

	

}
