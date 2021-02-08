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
class PedidoSecurityTest {
	
	private static final int TEST_PEDIDO_ID = 1;
	private static final int TEST_LINEAPEDIDO_ID = 1;

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
    void testObtenerPedido() throws Exception {
		mockMvc.perform(get("/pedidos/" + TEST_PEDIDO_ID)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/pedido"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    void testObtenerPedidoErroneo() throws Exception {
		mockMvc.perform(get("/pedidos/" + TEST_PEDIDO_ID)).andExpect(status().isForbidden());
	}
	
	@WithMockUser(username ="vendedor2",authorities = {"vendedor"})
    @Test
    void testGuardarEstadoPedido() throws Exception {
		mockMvc.perform(post("/pedidos/modificar/" + TEST_LINEAPEDIDO_ID +"/save").with(csrf())).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/vendedores/articulosVendidos"));
	}
	
	@WithMockUser(username ="cliente1",authorities = {"cliente"}) 
    @Test
    void testConfirmarCompra() throws Exception {
		mockMvc.perform(post("/pedidos/confirmarCompra").param("id", "1").with(csrf())).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/pedidos"));
	}
	
	@WithMockUser(username ="cliente3",authorities = {"cliente"}) 
    @Test
    void testConfirmarCompraError() throws Exception {
		mockMvc.perform(post("/pedidos/confirmarCompra").param("id", "1").with(csrf())).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(username ="cliente1",authorities = {"cliente"})
    @Test
    void testListadoPedidos() throws Exception {
		mockMvc.perform(get("/pedidos")).andExpect(status().is2xxSuccessful())
		.andExpect(view().name("clientes/listadoPedidos"));
	}
	
	@WithMockUser(username ="cliente2",authorities = {"cliente"})
    @Test
    void testConfirmarCompraErrorSinTarjeta() throws Exception {
		mockMvc.perform(post("/pedidos/confirmarCompra").with(csrf())).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	

	

}
