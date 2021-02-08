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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class MensajeSecurityTest {

	private static final int TEST_CLIENTE_ID = 1;
	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_ARTICULO_ID = 10;
	private static final String TEST_ROL_CLIENTE = "cliente";
	private static final String TEST_ROL_VENDEDOR = "vendedor";
	
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
    @DisplayName("Test Visualizar chat (como cliente)")
    void testVisualizaChatCliente() throws Exception {
		mockMvc.perform(get("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_ARTICULO_ID))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("users/chat"));
	}
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
    @DisplayName("Test de error Visualizar Chat (como cliente)")
    void testVisualizaChatClienteExcepcion() throws Exception {
		mockMvc.perform(get("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_ARTICULO_ID))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test Visualizar Chat (como vendedor)")
    void testVisualizaChatVendedor() throws Exception {
		mockMvc.perform(get("/chat/" + TEST_ROL_VENDEDOR + "/" + TEST_CLIENTE_ID))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("users/chat"));
	}
	
	@WithMockUser(username ="vendedor2",authorities = {"vendedor"})
    @Test
    @DisplayName("Test de error Visualizar Chat (como vendedor)")
    void testVisualizaChatVendedorExcepcion() throws Exception {
		mockMvc.perform(get("/chat/" + TEST_ROL_VENDEDOR + "/" + TEST_CLIENTE_ID))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(username ="cliente1",authorities = {"cliente"})
    @Test
    @DisplayName("Test Enviar mensaje (como cliente)")
    void testEnviarMensajeCliente() throws Exception {
		mockMvc.perform(post("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_VENDEDOR_ID + "/" + TEST_ARTICULO_ID)
				.param("version", "1")
				.param("texto", "Hola Buenos días").param("emisor", "49665433").param("receptor", "49697437")
				.param("lectura", "10").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
    @DisplayName("Test de error Enviar mensaje (como cliente)")
    void testEnviarMensajeClienteExcepcion() throws Exception {
		mockMvc.perform(post("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_VENDEDOR_ID + "/" + TEST_ARTICULO_ID)
				.param("version", "1")
				.param("texto", "Hola Buenos días").param("emisor", "49665433").param("receptor", "49697437")
				.param("lectura", "10").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
    
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    @DisplayName("Test Enviar mensaje (como vendedor)")
    void testEnviarMensajeVendedor() throws Exception {
		mockMvc.perform(post("/chat/" + TEST_ROL_VENDEDOR + "/" + TEST_CLIENTE_ID + "/" + TEST_CLIENTE_ID)
				.param("version", "1")
				.param("texto", "Hola Buenos días").param("emisor", "49697437").param("receptor", "49665433")
				.param("lectura", "10").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
	
	@WithMockUser(username ="vendedor2",authorities = {"vendedor"})
    @Test
    @DisplayName("Test de error Enviar mensaje (como vendedor)")
    void testEnviarMensajeVendedorExcepcion() throws Exception {
		mockMvc.perform(post("/chat/" + TEST_ROL_VENDEDOR + "/" + TEST_CLIENTE_ID + "/" + TEST_CLIENTE_ID)
				.param("version", "1")
				.param("texto", "Hola Buenos días").param("emisor", "49697437").param("receptor", "49665433")
				.param("lectura", "10").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
}
