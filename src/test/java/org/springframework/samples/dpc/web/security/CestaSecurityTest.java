package org.springframework.samples.dpc.web.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
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

class CestaSecurityTest {
	private static final int TEST_ARTICULO_ID = 1;

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
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
    void testAnyadirArticuloCesta() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
	
	@WithMockUser(username ="moderador1",authorities = {"moderador"})
    @Test
    void testAnyadirArticuloCestaModerador() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    void testAnyadirArticuloCestaVendedor() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().isForbidden());
	}
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
	void testActualizarCesta() throws Exception {
		mockMvc.perform(post("/cesta/actualizar").with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("clientes/cesta"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
	void testActualizarCestaVendedor() throws Exception {
		mockMvc.perform(post("/cesta/actualizar").with(csrf())).andExpect(status().isForbidden());
	}
	
	

}
