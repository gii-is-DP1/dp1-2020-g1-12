package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.PedidoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PedidoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class PedidoControllerTest {

	@MockBean
	private PedidoService pedidoService;

	@MockBean
	private CestaService cestaService;

	@MockBean
	private ClienteService clienteService;
	
	@BeforeEach
	void setup() {
		Pedido p = new Pedido();
		LineaPedido lp = new LineaPedido();
		Articulo a = new Articulo();
		List<LineaPedido> lpl = new ArrayList<>();
		Bloqueo bw = new Bloqueo();
		bw.setId(4);
		bw.setBloqueado(false);
		bw.setDescripcion("");
		Cliente c = new Cliente();
		c.setId(1);
		c.setDni("56789876");
		c.setNombre("Quique");
		c.setApellido("Salazar");
		c.setDireccion("C/Calle");
		c.setTelefono("615067389");
		c.setEmail("mail@mail.com");
		c.setBloqueo(bw);
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
		lp.setId(1);
		lp.setPedido(p);
		lp.setCantidad(2);
		lp.setPrecioUnitario(500.);
		lp.setArticulo(a);
		lpl.add(lp);
		p.setId(1);
		p.setPrecioTotal(1000.);
		p.setFecha(LocalDate.now());
		p.setLineas(lpl);
		p.setCliente(c);

		given(this.pedidoService.obtenerPedido(1)).willReturn(p);

	}

	@Autowired
	private MockMvc mockMvc;

//	@WithMockUser(value = "spring")
//	@Test
//	void testListadoPedido() throws Exception {
//		mockMvc.perform(get("/pedidos")).andExpect(status().isOk())
//				.andExpect(model().attributeExists("pedidos", "ordenacion")).andExpect(status().is2xxSuccessful())
//				.andExpect(view().name("clientes/listadoPedidos"));
//	}

	@WithMockUser(value = "spring")
	@Test
	void testTramitarPedido() throws Exception {
		mockMvc.perform(get("/pedidos/tramitarPedido")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/pedidos"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListadoPedidoId() throws Exception {
		mockMvc.perform(get("/pedidos/" + 1)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clientes/pedido"));
	}

}
