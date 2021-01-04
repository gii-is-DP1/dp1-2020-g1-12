package org.springframework.samples.dpc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
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
	
	private PedidoService pedidoService;

	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
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
	public String listadoPedido(ModelMap modelMap) {
		pedidoService.tramitarPedido();
		return "redirect:/pedidos";
	}
	
	@GetMapping("/{pedidoId}")
	public String listadoPedido(@PathVariable("pedidoId") Integer pedidoId, ModelMap modelMap) {
		Pedido pedido = pedidoService.obtenerPedido(pedidoId);
		List<LineaPedido> lineas = pedidoService.obtenerLineas(pedidoId);
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("lineas", lineas);
		return "clientes/pedido";
	}
	
}
