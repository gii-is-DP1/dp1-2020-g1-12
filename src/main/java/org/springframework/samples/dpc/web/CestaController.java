package org.springframework.samples.dpc.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.exceptions.CantidadNoValidaCestaException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cesta")
public class CestaController {

	private final CestaService cestaService;

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

	
	@GetMapping("/anyadirArticulo/{articuloId}")
	public String anyadirArticuloCesta(HttpServletRequest request, ModelMap modelMap,@PathVariable("articuloId") int articuloId) {
		cestaService.anyadirLineaCesta(articuloId);
		request.getSession().setAttribute("contador", cestaService.lineasCesta());
		return "redirect:/articulos/{articuloId}";
	}

	@PostMapping(value = "/actualizar")
	public String modificarArticulosCesta(Cesta cesta, ModelMap modelMap) {
		try {
			cestaService.actualizarCesta(cesta);
		} catch(CantidadNoValidaCestaException ex) {
			modelMap.addAttribute("error", "La cantidad de un artículo debe ser mayor a 0 y menor al stock del producto");
		}
		return listadoCesta(modelMap);
	}

	@GetMapping("/eliminar/{lineaId}")
	public String eliminarArticuloCesta(HttpServletRequest request, ModelMap modelMap, 
			@PathVariable("lineaId") int lineaId) {
		cestaService.eliminarLineaCesta(lineaId);
		request.getSession().setAttribute("contador", cestaService.lineasCesta());
		return "redirect:/cesta";
	}
}
