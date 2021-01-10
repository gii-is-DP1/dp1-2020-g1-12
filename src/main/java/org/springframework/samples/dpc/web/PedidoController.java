package org.springframework.samples.dpc.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	private final PedidoService pedidoService;
	private final CestaService cestaService;
	private final ClienteService clienteService;

	@Autowired
	public PedidoController(PedidoService pedidoService, CestaService cestaService, ClienteService clienteService) {
		this.pedidoService = pedidoService;
		this.cestaService = cestaService;
		this.clienteService = clienteService;
	}
	
	@GetMapping()
	public String listadoPedido(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, ModelMap modelMap) {
		Page<Pedido> pedidos = pedidoService.obtenerPedidos(page, size, orden);
		String signo = pedidos.getSort().get().findAny().get().isAscending() ? "" : "-";		//Guardo el parámetro de ordenación para que al cambiar
		String ordenacion = signo + pedidos.getSort().get().findAny().get().getProperty();	//de página se siga usando el filtro seleccionado
		
		modelMap.addAttribute("pedidos", pedidos);
		modelMap.addAttribute("ordenacion", ordenacion);
		return "clientes/listadoPedidos";
	}
	
	@GetMapping("/tramitarPedido")
	public String tramitarPedido(HttpServletRequest request, ModelMap modelMap) {
		Set<TarjetaCredito> tarjetas = clienteService.getClienteDeSesion().getTarjetas();
		Cesta cesta = cestaService.obtenerCestaCliente();
		String fechaEstimada = cestaService.fechaEstimada();
		modelMap.addAttribute("cesta", cesta);
		modelMap.addAttribute("tarjetas", tarjetas);
		modelMap.addAttribute("fechaEstimada", fechaEstimada);
		return "clientes/tramitar";
	}
	
	@GetMapping("/confirmarCompra")
	public String confirmarCompra(HttpServletRequest request, ModelMap modelMap) {
		pedidoService.tramitarPedido();
		request.getSession().setAttribute("contador", cestaService.lineasCesta());
		return "redirect:/pedidos";
	}
	
	@GetMapping("/{pedidoId}")
	public String obtenerPedido(@PathVariable("pedidoId") Integer pedidoId, ModelMap modelMap) {
		Pedido pedido = pedidoService.obtenerPedido(pedidoId);
		List<LineaPedido> lineas = pedidoService.obtenerLineas(pedidoId);
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("lineas", lineas);
		return "clientes/pedido";
	}
	
}
