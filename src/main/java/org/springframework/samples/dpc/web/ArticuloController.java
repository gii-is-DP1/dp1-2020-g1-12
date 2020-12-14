package org.springframework.samples.dpc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ArticuloController {
	
private final ArticuloService articuloService;
private final VendedorService vendedorService;
private final ComentarioService comentarioService;
	
	@Autowired
	public ArticuloController(ArticuloService articuloService, VendedorService vendedorService, ComentarioService comentarioService) {
		this.articuloService = articuloService;
		this.vendedorService = vendedorService;
		this.comentarioService = comentarioService;
	}
	
	@GetMapping()
	public String listadoArticulos(ModelMap modelMap) {
		String vista = "articulos/principal";

		List<Articulo> articulos = articuloService.articulosDisponibles();
		List<Articulo> ofertas = articuloService.ofertasRandomAcotada();
			
		modelMap.addAttribute("articulos", articulos);
		modelMap.addAttribute("ofertas", ofertas);
		modelMap.addAttribute("query", new Articulo());
		return vista;
	}
	
	@GetMapping(value="/articulos/{articuloId}")
	public String detallesArticulo(@PathVariable("articuloId") int articuloId, ModelMap modelMap) {
		String vista = "articulos/detalles";
		Articulo articulo = articuloService.findArticuloById(articuloId);
		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(articuloId);
		List<Comentario> comentarios = comentarioService.getComentariosDeUnArticulo(articuloId);
		List<Articulo> relacionados = articuloService.articulosRelacionados(articulo.getGeneros(), articuloId);
		Boolean puedeComentar = comentarioService.puedeComentar(articuloId);
		Double valoracion = comentarioService.getValoracionDeUnArticulo(articuloId);
		
		modelMap.addAttribute("articulo", articulo);
		modelMap.addAttribute("query", new Articulo());
		modelMap.addAttribute("vendedor", vendedor);
		modelMap.addAttribute("valoracion", valoracion);
		modelMap.addAttribute("puedeComentar", puedeComentar);
		modelMap.addAttribute("comentarios", comentarios);
		modelMap.addAttribute("relacionados", relacionados);
		return vista;
	}
	
	@PostMapping(value="/busqueda")
	public String busqueda(Articulo articulo,ModelMap modelMap) {
		String vista = "/articulos/principal";
		if(articulo.getModelo().isEmpty() && articulo.getGeneros() == null) {
			return listadoArticulos(modelMap);
		}
		List<Articulo> articulos= articuloService.busqueda(articulo);
		modelMap.addAttribute("query", new Articulo());
		modelMap.addAttribute("articulos", articulos);
		modelMap.addAttribute("mensaje", articuloService.mensajeDeBusqueda(articulo));
		return vista;
	}
	
	@GetMapping(value="/ofertas")
	public String listadoArticulosEnOfertas(ModelMap modelMap) {
		String vista = "/articulos/ofertas";
		List<Articulo> ofertas = articuloService.articulosOfertados();
		modelMap.addAttribute("ofertas", ofertas);
		return vista;
	}
}
