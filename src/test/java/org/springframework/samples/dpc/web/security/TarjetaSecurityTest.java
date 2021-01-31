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
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TarjetaSecurityTest {

	private static final int TEST_TARJETA_ID = 1;
	
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
    void testGuardarTarjeta() throws Exception {
    	mockMvc.perform(post("/tarjetas/save").param("titular", "Juan Alberto García").param("numero", "1234325678123909")
    						.param("cvv", "453")
    						.param("fechaCaducidad", "09/23")
    						.with(csrf()))
    			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes/perfil"));
    }
	
	@WithMockUser(username ="moderador1",authorities = {"moderador"})
    @Test
    void testGuardarTarjetaModerador() throws Exception {
    	mockMvc.perform(post("/tarjetas/save").param("titular", "Juan Alberto García").param("numero", "1234325678123909")
    						.param("cvv", "453")
    						.param("fechaCaducidad", "09/23")
    						.with(csrf()))
    			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
	
	@WithMockUser(username ="cliente3",authorities = {"cliente"})
    @Test
	 void testEliminarTarjeta() throws Exception {
			mockMvc.perform(get("/tarjetas/"+TEST_TARJETA_ID+"/delete"))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes/perfil"));
	}
	
	@WithMockUser(username ="vendedor1",authorities = {"vendedor"})
    @Test
	 void testEliminarTarjetaVendedor() throws Exception {
			mockMvc.perform(get("/tarjetas/"+TEST_TARJETA_ID+"/delete"))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
}
