package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.LineaPedidoService;
import org.springframework.samples.dpc.service.PedidoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PedidoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

class PedidoControllerTest {
	
	private static final int TEST_LINEAPEDIDO_ID = 1;
	private static final int TEST_PEDIDO_ID = 1;


	@MockBean
	private PedidoService pedidoService;

	@MockBean
	private LineaPedidoService lineaPedidoService;
	
	@MockBean
	private CestaService cestaService;
	
	@MockBean
	private ArticuloService articuloService;

	@MockBean
	private ClienteService clienteService;
	
	@BeforeEach
	void setup() {
		Pedido p = new Pedido();
		p.setVersion(1);
		LineaPedido lp = new LineaPedido();
		Articulo a = new Articulo();
		List<LineaPedido> lpl = new ArrayList<>();
		TarjetaCredito tarjeta = new TarjetaCredito();
		tarjeta.setId(1);
		tarjeta.setCvv("442");
		tarjeta.setFechaCaducidad("03/22");
		tarjeta.setNumero("4572240486955232");
		Set<TarjetaCredito> t = new HashSet<>();
		Cliente c = new Cliente();
		c.setId(1);
		c.setDni("56789876");
		c.setNombre("Quique");
		c.setApellido("Salazar");
		c.setDireccion("C/Calle");
		c.setTelefono("615067389");
		c.setEmail("mail@mail.com");
		tarjeta.setTitular("Juan Fernández Tirado");
		t.add(tarjeta);
		c.setTarjetas(t);
		a.setId(1);
		a.setModelo("Vant1");
		a.setMarca("Vant");
		a.setDescripcion("Mu bonito");
		a.setUrlImagen("www.com");
		a.setPrecio(500.);
		a.setStock(500);
		a.setTipo(Tipo.Nuevo);
		a.setTiempoEntrega(2);
		a.setGastoEnvio(2.);
		lp.setId(TEST_LINEAPEDIDO_ID);
		lp.setPedido(p);
		lp.setCantidad(2);
		lp.setPrecioUnitario(500.);
		lp.setArticulo(a);
		lpl.add(lp);
		p.setId(TEST_PEDIDO_ID);
		p.setPrecioTotal(1000.);
		p.setFecha(LocalDate.now());
		p.setLineas(lpl);
		p.setCliente(c);
		Cesta cesta = new Cesta();
		List<Pedido> pedidos = new ArrayList<>();
		List<LineaCesta> lineasC = new ArrayList<>();
		LineaCesta lineaC= new LineaCesta();
		lineaC.setArticulo(a);
		lineaC.setCantidad(1);
		lineaC.setCesta(cesta);
		lineaC.setId(9);
		lineaC.setVersion(1);
		lineasC.add(lineaC);
		cesta.setLineas(lineasC);
		pedidos.add(p);
		List<Integer> contador = new ArrayList<>(1);
		given(this.pedidoService.obtenerPedidos(0, 10, "-id"))
		.willReturn(new PageImpl<>(pedidos, PageRequest.of(0, 10, Sort.by(Order.desc("nombre"))), 10));
		given(this.pedidoService.obtenerLineas(1)).willReturn(Pair.of(lpl, contador));
		given(this.articuloService.obtenerFiltros(0, 10, "nombre", "pedidos")).willReturn(PageRequest.of(0, 10));
		given(this.clienteService.getClienteDeSesion()).willReturn(c);
		given(this.cestaService.obtenerCestaCliente()).willReturn(cesta);
		given(this.pedidoService.obtenerPedido(1)).willReturn(p);
		given(this.lineaPedidoService.obtenerLineaPedido(TEST_LINEAPEDIDO_ID)).willReturn(lp);
		given(this.lineaPedidoService.compruebaVendedorLinea(TEST_LINEAPEDIDO_ID)).willReturn(true);

	}

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Visualizar listado de pedidos")
	void testListadoPedido() throws Exception {
		mockMvc.perform(get("/pedidos")).andExpect(status().isOk())
				.andExpect(model().attributeExists("pedidos", "ordenacion")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/listadoPedidos"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Realizar pedido")
	void testTramitarPedido() throws Exception {
		mockMvc.perform(get("/pedidos/tramitarPedido")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/tramitar"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Detalles de pedido")
	void testListadoPedidoId() throws Exception {
		mockMvc.perform(get("/pedidos/" + 1)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/pedido"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Confirmar compra")
	void testConfirmarCompra() throws Exception {
		mockMvc.perform(post("/pedidos/confirmarCompra").param("tarjetaId", "1").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/pedidos"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Formulario actualizar estado de pedido")
	void testModificarEstadoPedido() throws Exception {
		mockMvc.perform(get("/pedidos/modificar/"+ TEST_LINEAPEDIDO_ID))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("vendedores/editarEstadoPedido"));
	}

	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Modificar estado de pedido (con error en id)")
	void testModificarEstadoPedidoConError() throws Exception {
		mockMvc.perform(get("/pedidos/modificar/9"))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/vendedores/articulosVendidos"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    @DisplayName("Test Actualizar estado del pedido")
	void testGuardarEstadoPedido() throws Exception {
		mockMvc.perform(post("/pedidos/modificar/"+ TEST_LINEAPEDIDO_ID+"/save").param("estado", "Pendiente").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/vendedores/articulosVendidos"));
	}

}
