package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.service.ArticuloService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

	private VendedorService vendedorService;
	private ArticuloService articuloService;
	private ClienteService clienteService;

	@Autowired
	public VendedorController(VendedorService vendedorService, ArticuloService articuloService,
			ClienteService clienteService) {
		this.vendedorService = vendedorService;
		this.articuloService = articuloService;
		this.clienteService = clienteService;
	}

	@GetMapping(value = "/perfil")
	public String mostrarPerfil(ModelMap modelMap) {
		String perfil = "vendedores/perfil";
		Optional<Vendedor> optperfil = vendedorService.datosPerfil(vendedorService.obtenerIdSesion());
		modelMap.addAttribute("vendedor", optperfil.get());
		return perfil;
	}

	public String salvarPerfil() {
		String perfil = "vendedores/salvarPerfil";
		return perfil;
	}

	public String guardarPerfil(@Valid Vendedor vendedor, BindingResult result, ModelMap modelMap) {
		String vista = "vendedores/perfil";
		if (result.hasErrors()) {
			modelMap.addAttribute("vendedor", vendedor);
			return "vendedor/editarPerfil";
		} else {
			vendedorService.guardar(vendedor);
			modelMap.addAttribute("mensage", "El vendedor ha sido guardado con éxito.");
		}
		return vista;
	}

	@GetMapping(value = "/editar")
	public String editar(Model model) {
		Vendedor vendedor = this.vendedorService.findSellerById(vendedorService.obtenerIdSesion());
		model.addAttribute(vendedor);
		return "vendedores/editarPerfil";
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Vendedor vendedor, BindingResult result) {
		if (result.hasErrors()) {
			return "vendedores/editarPerfil";
		} else {
			this.vendedorService.editar(vendedor, vendedorService.obtenerIdSesion());
			return "redirect:/vendedores/perfil";
		}
	}

	@GetMapping(value = "/perfilCliente/{clienteId}")
	public String mostrarPerfilCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
		Cliente cliente = this.clienteService.findClientById(clienteId);
		modelMap.addAttribute("cliente",cliente);
		modelMap.remove(cliente.getDni());
		return "vendedores/perfilCliente";
	}

	@GetMapping(value = "/articulosEnVenta")
	public String mostrarArticulosEnVenta(ModelMap modelMap) {
		String vista = "vendedores/listadoArticulos";
		Iterable<Articulo> optarticulos = articuloService.articulosEnVentaByProvider(vendedorService.obtenerIdSesion());
		modelMap.addAttribute("articulos", optarticulos);
		return vista;
	}

}
