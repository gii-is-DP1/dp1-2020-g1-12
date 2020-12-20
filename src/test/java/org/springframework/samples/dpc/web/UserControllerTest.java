package org.springframework.samples.dpc.web;

import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
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
	void testCreacionCliente() throws Exception {
		mockMvc.perform(post("/registro/cliente").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").param("username", "quique").param("password", "quique").with(csrf()))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreacionVendedor() throws Exception {
		mockMvc.perform(post("/registro/vendedor").param("dni", "56789876").param("nombre", "Quique")
				.param("apellido", "Salazar").param("direccion", "Calle Cuna").param("telefono", "615067389")
				.param("email", "mail@mail.com").param("username", "qui").param("password", "qui").with(csrf()))
				.andExpect(status().isOk());
	}

}
