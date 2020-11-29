package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.service.BloqueoService;
import org.springframework.samples.petclinic.service.SolicitudService;
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


@WebMvcTest(controllers=BloqueoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class BloqueoControllerTests {
	
	private static final int TEST_BLOQUEO_ID = 1;

	
	@Autowired(required=true)
	private BloqueoController bloqueoController;
	
	@MockBean 
	private BloqueoService bloqueoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Bloqueo bloqueo;
	
	@BeforeEach
	void setup() {
		bloqueo = new Bloqueo();
		bloqueo.setId(TEST_BLOQUEO_ID);
		bloqueo.setBloqueado(true);
		bloqueo.setDescripcion("Bloqueado por lenguaje inapropiado.");
		given(this.bloqueoService.findBlockById(TEST_BLOQUEO_ID)).willReturn(bloqueo);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testEdit() throws Exception {
		mockMvc.perform(get("/bloqueos/"+TEST_BLOQUEO_ID)).andExpect(model().attributeExists("bloqueo"))
		.andExpect(view().name("moderadores/editarBloqueo"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testBloquear() throws Exception{
		mockMvc.perform(post("/bloqueos/"+TEST_BLOQUEO_ID).param("bloqueado", "true").param("descripcion", "Está bloqueado por incumplir normas.").with(csrf()))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDesbloquear() throws Exception{
		mockMvc.perform(get("/bloqueos/desbloquear/"+TEST_BLOQUEO_ID))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes"));
	}
	
	
	
}