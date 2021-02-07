package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.service.OfertaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=OfertaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class OfertaControllerTest {
	
	private static final int TEST_OFERTA_ID = 1;
	private static final int TEST_ARTICULO_ID = 1;
	
	@MockBean 
	private OfertaService ofertaService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Oferta oferta;
	
	@BeforeEach
	void setup() {
		oferta = new Oferta();
		oferta.setVersion(1);
		oferta.setId(TEST_OFERTA_ID);
		oferta.setDisponibilidad(true);
		oferta.setPorcentaje(10);
		given(this.ofertaService.findOfertById(TEST_OFERTA_ID)).willReturn(oferta);
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario de crear oferta en artículo")
    void testEdit() throws Exception {
		mockMvc.perform(get("/vendedores/ofertas/"+TEST_OFERTA_ID+"/articulo/"+TEST_ARTICULO_ID)).
			andExpect(model().attributeExists("oferta")).andExpect(view().name("vendedores/editarOferta"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear oferta en artículo")
    void testProcesoOfertar() throws Exception{
		mockMvc.perform(post("/vendedores/ofertas/"+TEST_OFERTA_ID+"/articulo/"+TEST_ARTICULO_ID).param("version", "1").
				param("disponibilidad", "true").param("porcentaje", "10").with(csrf())).andExpect(status().
						is3xxRedirection()).andExpect(view().name("redirect:/vendedores/articulo/{articuloId}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear oferta en artículo (con error en porcentaje)")
    void testProcesoOfertarConErrores() throws Exception{
		mockMvc.perform(post("/vendedores/ofertas/"+TEST_OFERTA_ID+"/articulo/"+TEST_ARTICULO_ID).param("version", "1").
				param("disponibilidad", "true").param("porcentaje", "1").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("vendedores/editarOferta"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Eliminar oferta en artículo")
	void testProcesoDesofertar() throws Exception{
		mockMvc.perform(post("/vendedores/ofertas/desofertar/"+TEST_OFERTA_ID+"/articulo/"+TEST_ARTICULO_ID).with(csrf()).param("version", "1")).
			andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/vendedores/articulo/{articuloId}"));
	}
	
}
