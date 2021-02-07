package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.LineaCestaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CestaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

class CestaControllerTest {

	private static final int TEST_ARTICULO_ID = 2;

	@MockBean
	private CestaService cestaService;

	@MockBean
	private ArticuloService articuloService;

	@MockBean
	private LineaCestaService lineaCestaService;

	@BeforeEach
	void setup() {

		Cesta c = new Cesta();
		LineaCesta linea1 = new LineaCesta();
		linea1.setCantidad(1);
		linea1.setCesta(c);
		linea1.setId(1);
		linea1.setArticulo(articuloService.findArticuloById(1));
		LineaCesta linea2 = new LineaCesta();
		linea2.setCantidad(2);
		linea2.setCesta(c);
		linea2.setId(2);
		linea2.setArticulo(articuloService.findArticuloById(6));
		List<LineaCesta> lineas = new ArrayList<LineaCesta>();
		lineas.add(linea1);
		lineas.add(linea2);
		c.setLineas(lineas);

		given(this.cestaService.findCestaById(1)).willReturn(c);
	}

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar cesta")
	void testListadoCesta() throws Exception {
		mockMvc.perform(get("/cesta")).andExpect(status().isOk()).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/cesta"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test Añadir artículo a la cesta")
	void testAnyadirArticuloCesta() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/articulos/{articuloId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test Actualizar cesta")
	void testActualizarCesta() throws Exception {
		mockMvc.perform(post("/cesta/actualizar").with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("clientes/cesta"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test Eliminar artículo de cesta")
	void testEliminarArticuloCesta() throws Exception {
		mockMvc.perform(get("/cesta/eliminar/" + 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cesta"));
	}
}
