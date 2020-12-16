package org.springframework.samples.dpc.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.service.GeneroService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/generos")
public class GeneroController {

	private final GeneroService generoService;
	private final VendedorService vendedorService;

	
	@Autowired
	public GeneroController(GeneroService generoService, VendedorService vendedorService) {
		this.generoService = generoService;
		this.vendedorService = vendedorService;
	}
	
	@GetMapping(value = "/{articuloId}")
	public String iniciarFormulario(@PathVariable("articuloId") int articuloId, Model model) {
		List<Genero> generosDisponibles = generoService.generosRestantes(articuloId);
		model.addAttribute("genero", new Genero());
		model.addAttribute("articuloId",articuloId);
		model.addAttribute("generosDisponibles", generosDisponibles);
		return "vendedores/nuevoGenero";
	}
	
	@PostMapping(path = "/{articuloId}/save")
	public String guardarGenero(@PathVariable("articuloId") int articuloId, @Valid Genero genero, 
			BindingResult result,ModelMap modelMap) {
		String vista = "redirect:/vendedores/articulo/{articuloId}";
		if(result.hasErrors()) {
			modelMap.addAttribute("genero", new Genero());
			return "/generos/{articuloId}";
		} else {
			generoService.añadirGenero(articuloId, genero);
			return vista;
		}
	}
	
	@GetMapping(value = "/{articuloId}/{generoId}/remove")
	public String borrarGenero(@PathVariable("articuloId") int articuloId,
			@PathVariable("generoId") int generoId ,Model model) {
		
		if(generoService.findGeneroById(generoId) != null && vendedorService.esVendedorDelArticulo(articuloId)) {
			generoService.eliminarGenero(articuloId, generoId);
		}
		return "redirect:/vendedores/articulo/{articuloId}";
	}
	
}
