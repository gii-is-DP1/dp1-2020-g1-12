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
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.service.BloqueoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=BloqueoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class BloqueoControllerTests {
	
	private static final int TEST_BLOQUEO_ID = 1;

	
	@MockBean 
	private BloqueoService bloqueoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Bloqueo bloqueo;
	
	@BeforeEach
	void setup() {
		bloqueo = new Bloqueo();
		bloqueo.setId(TEST_BLOQUEO_ID);
		bloqueo.setVersion(1);
		bloqueo.setBloqueado(true);
		bloqueo.setDescripcion("Bloqueo por lenguaje inapropiado.");
		given(this.bloqueoService.findBlockById(TEST_BLOQUEO_ID)).willReturn(bloqueo);
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario editar bloqueo")
    void testEdit() throws Exception {
		mockMvc.perform(get("/bloqueos/"+TEST_BLOQUEO_ID)).andExpect(model().attributeExists("bloqueo"))
		.andExpect(view().name("moderadores/editarBloqueo"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Bloquear usuario")
    void testBloquear() throws Exception{
		mockMvc.perform(post("/bloqueos/"+TEST_BLOQUEO_ID).param("version", "1").param("bloqueado", "true").param("descripcion", "Está bloqueado por incumplir normas.").with(csrf()))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Bloquear usuario (con error en versión)")
    void testBloquearConError() throws Exception{
		mockMvc.perform(post("/bloqueos/"+TEST_BLOQUEO_ID).param("version", "2").param("bloqueado", "true").param("descripcion", "Está bloqueado por incumplir normas").with(csrf()))
		.andExpect(status().isOk()).andExpect(view().name("moderadores/editarBloqueo"));
	}
	
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Desbloquear usuario")
    void testDesbloquear() throws Exception{
		mockMvc.perform(get("/bloqueos/desbloquear/"+TEST_BLOQUEO_ID))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes"));
	}
}
