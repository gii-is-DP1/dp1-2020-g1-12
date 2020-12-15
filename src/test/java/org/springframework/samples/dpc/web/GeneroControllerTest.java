package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.GeneroService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=GeneroController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class GeneroControllerTest {
	
	private static final int TEST_GENERO_ID = 1;
	private static final int TEST_ARTICULO_ID = 1;

	@MockBean 
	private ArticuloService articuloService;
	
	@MockBean 
	private VendedorService vendedorService;
	
	@MockBean 
	private GeneroService generoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Genero genero;
	
	@BeforeEach
	void setup() {
		
		genero = new Genero();
		genero.setId(TEST_GENERO_ID);
		genero.setNombre("Smartphone");
		given(this.generoService.findGeneroById(TEST_GENERO_ID)).willReturn(genero);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testIniciarFormulario() throws Exception {
		mockMvc.perform(get("/generos/"+TEST_ARTICULO_ID )).andExpect(status().isOk())
		.andExpect(model().attributeExists("genero","articuloId","generosDisponibles")).andExpect(status().is2xxSuccessful())
		.andExpect(view().name("vendedores/nuevoGenero"));
	}

}
