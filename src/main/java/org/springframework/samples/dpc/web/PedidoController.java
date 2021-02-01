package org.springframework.samples.dpc.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.LineaPedidoService;
import org.springframework.samples.dpc.service.PedidoService;
import org.springframework.samples.dpc.service.exceptions.PedidoNoValidoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	private final PedidoService pedidoService;
	private final LineaPedidoService lineaPedidoService;
	private final CestaService cestaService;
	private final ClienteService clienteService;

	@Autowired
	public PedidoController(PedidoService pedidoService,LineaPedidoService lineaPedidoService, CestaService cestaService, ClienteService clienteService) {
		this.pedidoService = pedidoService;
		this.lineaPedidoService = lineaPedidoService;
		this.cestaService = cestaService;
		this.clienteService = clienteService;
	}
	
	@GetMapping()
	public String listadoPedido(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, ModelMap modelMap) {
		log.info("Entrando en la función Listado de Pedidos del controlador de Pedido.");

		Page<Pedido> pedidos = pedidoService.obtenerPedidos(page, size, orden);
		String signo = pedidos.getSort().get().findAny().get().isAscending() ? "" : "-";		//Guardo el parámetro de ordenación para que al cambiar
		String ordenacion = signo + pedidos.getSort().get().findAny().get().getProperty();	//de página se siga usando el filtro seleccionado
		
		modelMap.addAttribute("pedidos", pedidos);
		modelMap.addAttribute("ordenacion", ordenacion);
		return "clientes/listadoPedidos";
	}
	
	@GetMapping("/tramitarPedido")
	public String tramitarPedido(HttpServletRequest request, ModelMap modelMap) {
		log.info("Entrando en la función Tramitar un Pedido del controlador de Pedido.");
		
		
		Cesta cesta = cestaService.obtenerCestaCliente();
		if(cesta.getLineas().isEmpty()) {
			return "redirect:/";
		}
		Set<TarjetaCredito> tarjetas = clienteService.getClienteDeSesion().getTarjetas();
		String fechaEstimada = cestaService.fechaEstimada();

		modelMap.addAttribute("cesta", cesta);
		modelMap.addAttribute("tarjetaSel", new TarjetaCredito());
		modelMap.addAttribute("tarjetas", tarjetas);
		modelMap.addAttribute("fechaEstimada", fechaEstimada);
		return "clientes/tramitar";
	}
	
	
	@PostMapping("/confirmarCompra")
	public String confirmarCompra(TarjetaCredito tarjeta, HttpServletRequest request, ModelMap modelMap) {
		log.info("Entrando en la función Confirmar Compra del controlador de Pedido.");
		
		if(clienteService.getClienteDeSesion().getTarjetas().isEmpty() ) {
			modelMap.put("message", "Es necesario añadir una tarjeta para realizar la compra.");
			return tramitarPedido(request,modelMap);
		}
		try {
			pedidoService.tramitarPedido(tarjeta.getId());
			
		}catch(PedidoNoValidoException e) {
			log.warn("La función confirmar compra");
			modelMap.put("message", "La cantidad de uno o varios productos elegidos supera el stock de los artículo, compruébelos antes de realizar la compra.");
			return tramitarPedido(request,modelMap);
		}
		request.getSession().setAttribute("contador", cestaService.lineasCesta());
		return "redirect:/pedidos";
	}
	
	@GetMapping("/{pedidoId}")
	public String obtenerPedido(@PathVariable("pedidoId") Integer pedidoId, ModelMap modelMap) {
		log.info("Entrando en la función Obtener un Pedido del controlador de Pedido.");

		Pedido pedido = pedidoService.obtenerPedido(pedidoId);
		Pair<List<LineaPedido>, List<Integer>> lineas = pedidoService.obtenerLineas(pedidoId);
		Double gastosEnvio = pedidoService.obtenerGastoEnvio(pedido, lineas.getFirst());
		modelMap.addAttribute("pedido", pedido);
		modelMap.addAttribute("lineas", lineas.getFirst());
		modelMap.addAttribute("contadores", lineas.getSecond());
		modelMap.addAttribute("gastos", gastosEnvio);
		return "clientes/pedido";
	}
	
	@GetMapping("/modificar/{lineaPedidoId}")
	public String modificarEstadoPedido(@PathVariable("lineaPedidoId") Integer lineaPedidoId, ModelMap modelMap) {
		LineaPedido lineaPedido = lineaPedidoService.obtenerLineaPedido(lineaPedidoId);
		if(lineaPedido == null || !lineaPedidoService.compruebaVendedorLinea(lineaPedidoId)) {
			return "redirect:/vendedores/articulosVendidos";
		}
		modelMap.addAttribute("lineaPedido", lineaPedido);
		return "vendedores/editarEstadoPedido";
	}
	
	@PostMapping("/modificar/{lineaPedidoId}/save")
	public String guardarEstadoPedido(@PathVariable("lineaPedidoId") int lineaPedidoId, LineaPedido lineaPedido, ModelMap modelMap) {
		if(lineaPedido != null && lineaPedidoService.compruebaVendedorLinea(lineaPedidoId)) {
			lineaPedidoService.actualizarEstado(lineaPedidoId, lineaPedido);
		}
		return "redirect:/vendedores/articulosVendidos";
	}
}
