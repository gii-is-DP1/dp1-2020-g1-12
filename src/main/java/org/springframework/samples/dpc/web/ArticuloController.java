package org.springframework.samples.dpc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.samples.dpc.service.GeneroService;
import org.springframework.samples.dpc.service.VendedorService;
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
@RequestMapping("/")
public class ArticuloController {

	private final ArticuloService articuloService;
	private final VendedorService vendedorService;
	private final ComentarioService comentarioService;
	private final CestaService cestaService;
	private final GeneroService generoService;
	private final ClienteService clienteService;
	private static final String generos = "generos";
	private static final String query = "query";

	@Autowired
	public ArticuloController(ArticuloService articuloService, VendedorService vendedorService,
			ComentarioService comentarioService, GeneroService generoService, CestaService cestaService,
			ClienteService clienteService) {
		this.articuloService = articuloService;
		this.vendedorService = vendedorService;
		this.comentarioService = comentarioService;
		this.generoService = generoService;
		this.cestaService = cestaService;
		this.clienteService = clienteService;
	}

	@GetMapping()
	public String listadoArticulos(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, ModelMap modelMap) {
		log.info("Entrando en la función Listado de Artículo del controlador de Artículo.");
		Page<Articulo> articulos = articuloService.articulosDisponibles(page, size, orden);
		List<Articulo> ofertas = articuloService.ofertasRandomAcotada();
		String signo = articulos.getSort().get().findAny().get().isAscending() ? "" : "-"; // Guardo el parámetro de
																							// ordenación para que al
																							// cambiar
		String ordenacion = signo + articulos.getSort().get().findAny().get().getProperty(); // de página se siga usando
																								// el filtro
																								// seleccionado

		modelMap.addAttribute(generos, generoService.findAllGeneros());
		modelMap.addAttribute("articulos", articulos);
		modelMap.addAttribute("ordenacion", ordenacion);
		modelMap.addAttribute("ofertas", ofertas);
		modelMap.addAttribute(query, new Articulo());
		return "articulos/principal";
	}

	@GetMapping(value = "/articulos/{articuloId}")
	public String detallesArticulo(@PathVariable("articuloId") int articuloId, ModelMap modelMap) {
		log.info("Entrando en la función Detalles de un Artículo del controlador de Artículo.");

		Articulo articulo = articuloService.findArticuloById(articuloId);
		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(articuloId);
		List<Comentario> comentarios = comentarioService.getComentariosDeUnArticulo(articuloId);
		List<Articulo> relacionados = articuloService.articulosRelacionados(articulo);
		Boolean puedeComentar = comentarioService.puedeComentar(articuloId);
		Double valoracion = comentarioService.getValoracionDeUnArticulo(articuloId);
		Boolean puedeComprar = cestaService.articuloEnCesta(articuloId);
		Integer puedeEditarCliente = clienteService.obtenerIdSesion();
		Integer puedeEditarVendedor = vendedorService.obtenerIdSesion();

		modelMap.addAttribute(generos, generoService.findAllGeneros());
		modelMap.addAttribute("articulo", articulo);
		modelMap.addAttribute(query, new Articulo());
		modelMap.addAttribute("vendedor", vendedor);
		modelMap.addAttribute("valoracion", valoracion);
		modelMap.addAttribute("puedeComentar", puedeComentar);
		modelMap.addAttribute("puedeComprar", puedeComprar);
		modelMap.addAttribute("comentarios", comentarios);
		modelMap.addAttribute("relacionados", relacionados);
		modelMap.addAttribute("puedeEditarCliente", puedeEditarCliente);
		modelMap.addAttribute("puedeEditarVendedor", puedeEditarVendedor);
		return "articulos/detalles";
	}

	@PostMapping(value = "/busqueda")
	public String busqueda(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, Articulo articulo,
			ModelMap modelMap) {
		log.info("Entrando en la función Búsqueda de un Artículo del controlador de Artículo.");

		String vista = "/articulos/principal";
		if (articulo.getModelo().isEmpty() && articulo.getGeneros() == null) {
			return "redirect:/";
		}
		Page<Articulo> articulos = articuloService.busqueda(articulo, page, size, orden);
		modelMap.addAttribute(generos, generoService.findAllGeneros());
		modelMap.addAttribute(query, new Articulo());
		modelMap.addAttribute("articulos", articulos);
		modelMap.addAttribute("mensaje", articuloService.mensajeDeBusqueda(articulo));
		return vista;
	}

	@GetMapping(value = "/ofertas")
	public String listadoArticulosEnOfertas(ModelMap modelMap) {
		log.info("Entrando en la función Listado de Artículos en Oferta del controlador de Artículo.");

		String vista = "/articulos/ofertas";
		List<Articulo> ofertas = articuloService.articulosOfertados();
		modelMap.addAttribute("ofertas", ofertas);
		return vista;
	}
}
