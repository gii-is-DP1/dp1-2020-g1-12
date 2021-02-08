package org.springframework.samples.dpc.web.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
	
	@WithMockUser(username ="cliente1",authorities = {"cliente"})
    @Test
    @DisplayName("Test Añadir artículo a la cesta (como cliente)")
    void testAnyadirArticuloCesta() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
	
	@WithMockUser(username ="moderador1",authorities = {"moderador"})
    @Test
    @DisplayName("Test de error Añadir artículo a la cesta (como moderador)")
    void testAnyadirArticuloCestaModerador() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().isForbidden());
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test de error Añadir artículo a la cesta (como vendedor)")
    void testAnyadirArticuloCestaVendedor() throws Exception {
		mockMvc.perform(get("/cesta/anyadirArticulo/" + TEST_ARTICULO_ID)).andExpect(status().isForbidden());
	}
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
    @DisplayName("Test Actualizar cesta (como cliente)")
	void testActualizarCesta() throws Exception {
		mockMvc.perform(post("/cesta/actualizar").with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("clientes/cesta"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test de error Actualizar cesta (como vendedor)")
	void testActualizarCestaVendedor() throws Exception {
		mockMvc.perform(post("/cesta/actualizar").with(csrf())).andExpect(status().isForbidden());
	}
	
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
    @DisplayName("Test Eliminar artículo de la cesta (como cliente)")
	void testEliminarArticuloCesta() throws Exception {
		mockMvc.perform(get("/cesta/eliminar/" + 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cesta"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test de error Eliminar artículo de la cesta (como vendedor)")
	void testEliminarArticuloCestaVendedor() throws Exception {
		mockMvc.perform(get("/cesta/eliminar/" + 1)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@WithMockUser(username ="cliente1",authorities = {"cliente"})
    @Test
    @DisplayName("Test Obtener listado de cesta (como cliente)")
	void testListadoCesta() throws Exception {
		mockMvc.perform(get("/cesta")).andExpect(status().isOk()).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/cesta"));
	}

	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test de error Obtener listado de cesta (como vendedor)")
	void testListadoCestaVendedor() throws Exception {
		mockMvc.perform(get("/cesta")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@WithMockUser(username ="moderador1",authorities = {"moderador"})
    @Test
    @DisplayName("Test de error Obtener listado de cesta (como moderador)")
	void testListadoCestaModerador() throws Exception {
		mockMvc.perform(get("/cesta")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
}
