package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.service.TarjetaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=TarjetaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

class TarjetaControllerTest {
	
	private static final int TEST_TARJETA_ID = 1;
	
	@MockBean 
	private TarjetaService tarjetaService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private TarjetaCredito tarjeta;
	
	@BeforeEach
	void setup() {
		
		tarjeta = new TarjetaCredito();
		tarjeta.setId(TEST_TARJETA_ID);
		tarjeta.setNumero("1234567899876087");
		tarjeta.setTitular("Juan Fernández Tirado");
		tarjeta.setCvv("442");
		tarjeta.setFechaCaducidad("03/22");
		
		given(this.tarjetaService.findTarjetaById(TEST_TARJETA_ID)).willReturn(tarjeta);
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario de crear tarjeta")
    void testIniciarFormulario() throws Exception {
		mockMvc.perform(get("/tarjetas/new")).andExpect(status().isOk()).andExpect(model().attributeExists("tarjeta"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("clientes/editarTarjeta"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear tarjeta")
    void testGuardarTarjeta() throws Exception {
	mockMvc.perform(post("/tarjetas/save").param("titular", "Juan Alberto García").param("numero", "1234325678123909")
						.param("cvv", "453")
						.param("fechaCaducidad", "09/23")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes/perfil"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear tarjeta (con error en numero)")
    void testGuardarTarjetaErroneo() throws Exception {
	mockMvc.perform(post("/tarjetas/save").param("titular", "Juan Alberto García").param("numero", "123409")
						.param("cvv", "453")
						.param("fechaCaducidad", "09/23")
						.with(csrf())).andExpect(model().attributeExists("tarjeta"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("clientes/editarTarjeta"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Eliminar tarjeta")
    void testEliminarTarjeta() throws Exception {
	mockMvc.perform(get("/tarjetas/"+TEST_TARJETA_ID+"/delete"))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clientes/perfil"));
	}

}
