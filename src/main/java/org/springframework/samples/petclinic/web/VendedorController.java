package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.SolicitudService;
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

	@Autowired
	private VendedorService vendedorService;

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{vendedorId}")
	public String mostrarPerfil(@PathVariable("vendedorId") Integer vendedorId, ModelMap modelMap) {
		String perfil = "vendedores/perfil";
		Optional<Vendedor> optperfil = vendedorService.datosPerfil(vendedorId);
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

	@GetMapping(value = "/{vendedorId}/editar")
	public String editar(@PathVariable("vendedorId") int vendedorId, Model model) {
		Vendedor vendedor = this.vendedorService.findSellerById(vendedorId);
		model.addAttribute(vendedor);
		return "vendedores/editarPerfil";
	}

	@PostMapping(value = "/{vendedorId}/editar")
	public String procesoEditar(@Valid Vendedor vendedor, BindingResult result,
			@PathVariable("vendedorId") int vendedorId) {
		if (result.hasErrors()) {
			return "vendedores/editarPerfil";
		} else {
			this.vendedorService.editar(vendedor, vendedorId);
			return "redirect:/vendedores/{vendedorId}";
		}
	}

	@GetMapping(value = "/{vendedorId}/perfilCliente/{clienteId}")
	public String mostrarPerfilCliente(@PathVariable("vendedorId") int vendedorId,
			@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
		Cliente cliente = this.clienteService.findClientById(clienteId);
		modelMap.addAttribute(cliente);
		modelMap.remove(cliente.getDni());
		return "vendedores/perfilCliente";
	}

	@GetMapping(value = "/{vendedorId}/articulosEnVenta")
	public String mostrarArticulosVendidos(@PathVariable("vendedorId") Integer vendedorId, ModelMap modelMap) {
		String vista = "vendedores/listadoArticulos";
		Iterable<Articulo> optarticulos = solicitudService
				.articulosEnVentaByProvider(vendedorService.findSellerById(vendedorId).getSolicitudes());
		modelMap.addAttribute("articulos", optarticulos);
		return vista;
	}

}
