package org.springframework.samples.dpc.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.PedidoRepository;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cesta")
public class CestaController {

	private CestaService cestaService;

	@Autowired
	public CestaController(CestaService cestaService) {
		this.cestaService = cestaService;
	}

	@GetMapping()
	public String listadoCesta(ModelMap modelMap) {
		Cesta cesta = cestaService.obtenerCestaCliente();
		modelMap.addAttribute("cesta", cesta);
		return "clientes/cesta";
	}

	@GetMapping("/añadirArticulo/{articuloId}")
	public String anyadirArticuloCesta(ModelMap modelMap, @PathVariable("articuloId") int articuloId) {
		cestaService.anyadirLineaCesta(articuloId);
		return "redirect:/articulos/{articuloId}";
	}

	@PostMapping(value = "/actualizar")
	public String modificarArticulosCesta(Cesta cesta) {
		cestaService.actualizarCesta(cesta);
		return "redirect:/cesta";
	}

	@GetMapping("/eliminar/{lineaId}")
	public String eliminarArticuloCesta(ModelMap modelMap, @PathVariable("lineaId") int lineaId) {
		cestaService.eliminarLineaCesta(lineaId);
		return "redirect:/cesta";
	}

	@GetMapping("/tramitarPedido")
	public String tramitarPedido(ModelMap modelMap) {
		List<LineaCesta> lineas = cestaService.obtenerCestaCliente().getLineas();
		Pedido p = new Pedido();
		List<LineaPedido> clp = new ArrayList<>();
		for (LineaCesta linea : lineas) {
			Articulo articulo = linea.getArticulo();
			articulo.setStock(articulo.getStock() - linea.getCantidad());
			LineaPedido lp = new LineaPedido();
			lp.setId((int) Math.floor(Math.random()));
			lp.setPedido(p);
			lp.setPrecioUnitario(articulo.getPrecio());
			lp.setCantidad(linea.getCantidad());
			lp.setArticulo(articulo);
//			this.cestaService.guardarLineaPedido(lp);
			this.cestaService.actualizarCesta(cestaService.obtenerCestaCliente());
			clp.add(lp);
		}
		p.setId((int) Math.floor(Math.random()));
		p.setLineas(clp);
		p.setFecha(LocalDate.now());
		p.setPrecioTotal(cestaService.obtenerCestaCliente().getPrecioFinal());
		modelMap.put("pedido", p);
		this.cestaService.eliminarLineasCesta(lineas);
		this.cestaService.actualizarCesta(cestaService.obtenerCestaCliente());
		return "redirect:/";
	}

//	@PostMapping(value = "/tramitarPedido")
//	public String processCreationFormVendedor(@Valid Pedido p, BindingResult result) {
//		if (result.hasErrors()) {
//			return "redirect:/cesta";
//		} else {
//			List<LineaCesta> lineas = cestaService.obtenerCestaCliente().getLineas();
//			List<LineaPedido> clp = new ArrayList<>();
//			for (LineaCesta linea : lineas) {
//				Articulo articulo = linea.getArticulo();
//				articulo.setStock(articulo.getStock() - linea.getCantidad());
//				LineaPedido lp = new LineaPedido();
//				lp.setId((int) Math.floor(Math.random()));
//				lp.setPedido(p);
//				lp.setPrecioUnitario(articulo.getPrecio());
//				lp.setCantidad(linea.getCantidad());
//				lp.setArticulo(articulo);
//				this.cestaService.guardarLineaPedido(lp);
//				this.cestaService.actualizarCesta(cestaService.obtenerCestaCliente());
//				clp.add(lp);
//			}
//			p.setId((int) Math.floor(Math.random()));
//			p.setLineas(clp);
//			p.setFecha(LocalDate.now());
//			p.setPrecioTotal(cestaService.obtenerCestaCliente().getPrecioFinal());
//			this.cestaService.guardarPedido(p);
//			this.cestaService.eliminarLineasCesta(lineas);
//			this.cestaService.actualizarCesta(cestaService.obtenerCestaCliente());
//			return "redirect:/";
//		}
//	}

}
