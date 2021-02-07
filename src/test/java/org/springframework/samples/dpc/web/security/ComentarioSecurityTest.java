package org.springframework.samples.dpc.web.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
class ComentarioSecurityTest {

	private static final int TEST_ARTICULO_ID = 10;
	
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
	
	@WithMockUser(username ="cliente3",authorities = {"cliente"})
    @Test
    void testProcesoComentar() throws Exception {
		mockMvc.perform(post("/comentario/articulo/" + TEST_ARTICULO_ID).param("descripcion", "asdfgasdfasdfassdfdfdf")
				.param("valoracion", "3").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
    void testProcesoComentarVendedorArticuloPropio() throws Exception {
		mockMvc.perform(post("/comentario/articulo/" + TEST_ARTICULO_ID).param("descripcion", "ASDFGHJKLÑQWUYEIASDF")
				.param("valoracion", "0").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
	
	@WithMockUser(username ="vendedor3",authorities = {"vendedor"})
    @Test
    void testProcesoComentarVendedorArticuloAjeno() throws Exception {
		mockMvc.perform(post("/comentario/articulo/" + TEST_ARTICULO_ID).param("descripcion", "ASDFGHJKLÑQWUYEIASDF")
				.param("valoracion", "3").with(csrf()))
				.andExpect(view().name("articulos/editarComentario"));
	}

}
