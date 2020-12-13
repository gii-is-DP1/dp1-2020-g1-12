package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.dpc.model.Authorities;
import org.springframework.samples.dpc.model.Moderador;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.service.ModeradorService;
import org.springframework.samples.dpc.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ModeradorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class ModeradorControllerTests {
	
	private static final int TEST_MODERADOR_ID = 1;

	@Autowired(required=true)
	private ModeradorController moderadorController;
	
	@MockBean 
	private ModeradorService moderadorService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Moderador moderador;
	
	private User user;
	
	
	@BeforeEach
	void setup() {
		moderador = new Moderador();
		user = new User();
		user.setUsername("moderador10");
		user.setPassword("moderador10");
		user.setEnabled(true);
		Authorities a = new Authorities();
		a.setUser(user);
		a.setAuthority("moderador");
		a.setId(1);
		
		moderador.setId(TEST_MODERADOR_ID);
		moderador.setUser(user);
		moderador.setNombre("Pepe");
		moderador.setApellido("López");
		moderador.setDireccion("c/Real, 10");
		moderador.setDni("12345678");
		moderador.setTelefono("610111214");
		
		given(this.moderadorService.findModeradorById(TEST_MODERADOR_ID)).willReturn(moderador);
	
	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testMostrarPerfil() throws Exception {
//		mockMvc.perform(get("/moderadores/perfil")).andExpect(status().is2xxSuccessful())
//		.andExpect(model().attributeExists("moderador"))
//		.andExpect(view().name("/moderadores/perfil"));
//	}
	
//	@WithMockUser(value = "spring")
//    @Test
//    void testEdit() throws Exception {
//		mockMvc.perform(get("/moderadores/editar")).andExpect(status().is2xxSuccessful())
//		.andExpect(model().attributeExists("moderador"))
//		.andExpect(view().name("redirect:/moderadores/perfil"));
//	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcesoEditar() throws Exception {
		mockMvc.perform(post("/moderadores/editar").param("id", "1").param("user", "moderador10")
		.param("nombre", "Pepe").param("apellido", "López").param("direccion", "C/Real 10")
		.param("dni", "12345678").param("telefono", "678901234").with(csrf())).andExpect(status().is2xxSuccessful())
		.andExpect(view().name("moderadores/editarPerfil"));

	}

}
