package org.springframework.samples.dpc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ArticuloController {
	
private final ArticuloService articuloService;
	
	@Autowired
	public ArticuloController(ArticuloService articuloService) {
		this.articuloService = articuloService;
	}
	
	@GetMapping()
	public String listadoArticulos(ModelMap modelMap) {
		String vista = "articulos/principal";

		List<Articulo> articulos = articuloService.articulosDisponibles();
		modelMap.addAttribute("articulos", articulos);
		return vista;
	}
	
}
