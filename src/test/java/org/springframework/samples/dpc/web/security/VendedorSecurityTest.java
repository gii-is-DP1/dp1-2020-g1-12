package org.springframework.samples.dpc.web.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class VendedorSecurityTest {
	

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() {
	mockMvc = MockMvcBuilders
	.webAppContextSetup(context)
	.apply(SecurityMockMvcConfigurers.springSecurity())
	.build();
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test Mostrar artículos vendidos (como vendedor)")
    void testMostrarArticulosObtenidos() throws Exception {
		mockMvc.perform(get("/vendedores/articulosVendidos")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("vendedores/listadoArticulosVendidos"));
	}

}
