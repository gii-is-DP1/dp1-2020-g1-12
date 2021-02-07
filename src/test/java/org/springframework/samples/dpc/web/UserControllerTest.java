package org.springframework.samples.dpc.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.service.AuthoritiesService;
import org.springframework.samples.dpc.service.BloqueoService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class UserControllerTest {

	@MockBean
	private VendedorService vendedorService;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private BloqueoService bloqueoService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
	@Test
	void testIniForm() throws Exception {
		mockMvc.perform(get("/registro")).andExpect(view().name("users/registro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreacionCliente() throws Exception {
		mockMvc.perform(post("/registro/cliente").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").param("user.username", "quique").param("user.password", "Quique100").with(csrf()))
				.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreacionClienteConErrores() throws Exception {
		mockMvc.perform(post("/registro/cliente").param("dni", "5678987").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "689")
				.param("email", "mail@mail.com").param("user.username", "q").param("user.password", "quique").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("users/registroCliente"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreacionVendedor() throws Exception {
		mockMvc.perform(post("/registro/vendedor").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").param("user.username", "quique").param("user.password", "Quique100").with(csrf()))
				.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreacionVendedorConErrores() throws Exception {
		mockMvc.perform(post("/registro/vendedor").param("dni", "5678987").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "689")
				.param("email", "mail@mail.com").param("user.username", "q").param("user.password", "quique").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("users/registroVendedor"));
	}
}
