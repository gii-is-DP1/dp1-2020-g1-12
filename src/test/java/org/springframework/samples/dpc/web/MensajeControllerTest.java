package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.LineaPedidoService;
import org.springframework.samples.dpc.service.MensajeService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.samples.dpc.service.exceptions.MensajeProhibidoException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=MensajeController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MensajeControllerTest {
	
	private static final int TEST_CLIENTE_ID = 1;
	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_ARTICULO_ID = 10;
	private static final String TEST_ROL_CLIENTE = "cliente";
	private static final String TEST_ROL_VENDEDOR = "vendedor";
	
	@MockBean 
	private MensajeService mensajeService;
	
	@MockBean 
	private VendedorService vendedorService;
	
	@MockBean 
	private ClienteService clienteService;
	
	@MockBean 
	private LineaPedidoService lineaPedidoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() throws MensajeProhibidoException {
		Cliente c = new Cliente();
		c.setId(TEST_CLIENTE_ID);
		c.setVersion(1);
		c.setDni("49879954");
		c.setNombre("Pepe");
		c.setApellido("Lorca");
		c.setDireccion("C/Real");
		c.setTelefono("698768798");
		c.setEmail("new@gmail.com");
		
		Vendedor vend = new Vendedor();
		vend = new Vendedor();
		vend.setId(TEST_VENDEDOR_ID);
		vend.setDni("42833974");
		vend.setNombre("Juan");
		vend.setApellido("Pérez");
		vend.setDireccion("C/Buenavista");
		vend.setTelefono("698576572");
		vend.setEmail("example@gmail.com");
		
		List<String> aux = new ArrayList<>();
		aux.add("49786533");
		aux.add("Pepe Lorca");
		aux.add("4");
		
		given(this.clienteService.getClienteDeSesion()).willReturn(c);
		given(this.vendedorService.vendedorDeUnArticulo(TEST_ARTICULO_ID)).willReturn(vend);		
		given(this.clienteService.getValidaChat(TEST_ARTICULO_ID, TEST_CLIENTE_ID)).willReturn(true);
		given(this.vendedorService.findSellerById(TEST_VENDEDOR_ID)).willReturn(vend);		
		
		given(this.clienteService.findClientById(TEST_CLIENTE_ID)).willReturn(c);		
		given(this.vendedorService.getVendedorDeSesion()).willReturn(vend);
		given(this.lineaPedidoService.articulosVendidosByProvider(0, 100, "-id", TEST_VENDEDOR_ID))
		.willReturn(new PageImpl<>(new ArrayList<LineaPedido>(), PageRequest.of(0, 10, 
				Sort.by(Order.desc("id"))), 10));
		given(this.lineaPedidoService.esComprador(new ArrayList<Integer>(), TEST_CLIENTE_ID))
		.willReturn(true);
		
		given(this.mensajeService.obtenerMensajes(TEST_ROL_CLIENTE, TEST_CLIENTE_ID))
		.willReturn(Pair.of(new ArrayList<Mensaje>(), aux));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Visualizar chat en cliente")
    void testVisualizarChatCliente() throws Exception {
		mockMvc.perform(get("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_CLIENTE_ID)).
			andExpect(model().attributeExists("dni")).andExpect(model().attributeExists("nombre"))
			.andExpect(model().attributeExists("receptorId")).andExpect(model().attributeExists("id"))
			.andExpect(model().attributeExists("mensajes")).andExpect(model().attributeExists("nuevoMensaje"))
			.andExpect(view().name("users/chat"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Enviar mensaje en cliente")
    void testEnviarMensajeCliente() throws Exception{
		mockMvc.perform(post("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_VENDEDOR_ID + "/" + TEST_ARTICULO_ID)
				.param("version", "1")
				.param("texto", "Hola Buenos días").param("emisor", "49665433").param("receptor", "49697437")
				.param("lectura", "10").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Enviar mensaje en cliente (con error en el texto)")
    void testEnviarMensajeClienteConError() throws Exception{
		mockMvc.perform(post("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_VENDEDOR_ID + "/" + TEST_ARTICULO_ID)
				.param("version", "1")
				.param("texto", "").param("emisor", "49665433").param("receptor", "49697437")
				.param("lectura", "10").with(csrf()))
				.andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Enviar mensaje en vendedor")
    void testEnviarMensajeVendedor() throws Exception{
		mockMvc.perform(post("/chat/" + TEST_ROL_VENDEDOR + "/" + TEST_CLIENTE_ID + "/" + TEST_CLIENTE_ID)
				.param("version", "1")
				.param("texto", "Hola Buenos días").param("emisor", "49697437").param("receptor", "49665433")
				.param("lectura", "01").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Enviar mensaje vendedor (con error en texto)")
    void testEnviarMensajeVendedorConError() throws Exception{
		mockMvc.perform(post("/chat/" + TEST_ROL_CLIENTE + "/" + TEST_VENDEDOR_ID + "/" + TEST_ARTICULO_ID)
				.param("version", "1")
				.param("texto", "").param("emisor", "49697437").param("receptor", "49665433")
				.param("lectura", "10").with(csrf()))
				.andExpect(view().name("redirect:/chat/{rol}/{id}"));
	}
}
