package org.springframework.samples.dpc.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	private final VendedorService vendedorService;
	
	@Autowired
	public ClienteController(ClienteService clienteService, VendedorService vendedorService) {
		this.clienteService = clienteService;
		this.vendedorService = vendedorService;
	}

	@GetMapping(value="/perfil")
	public String mostrarPerfil(ModelMap modelMap){
		String  perfil="clientes/perfil";
		Optional<Cliente> optperfil = clienteService.datosPerfil(clienteService.obtenerIdSesion());		
		modelMap.addAttribute("cliente", optperfil.get());
		return perfil;
	}
	
//	public String salvarPerfil() {
//		String perfil = "clientes/salvarPerfil";
//		return perfil;
//	}
    
//	public String guardarPerfil(@Valid Cliente cliente,BindingResult result, ModelMap modelMap) {
//		String vista="clientes/perfil";
//		if(result.hasErrors()) {
//			modelMap.addAttribute("cliente", cliente);
//			return "cliente/editarPerfil";
//		}else {
//			clienteService.guardar(cliente);
//			modelMap.addAttribute("mensage", "El cliente ha sido guardado con éxito.");			
//		}
//		return vista;
//	}
	
	@GetMapping(value = "/editar")
	public String editar(Model model) {
		Cliente cliente = this.clienteService.findClientById(clienteService.obtenerIdSesion());
		model.addAttribute(cliente);
		return "clientes/editarPerfil";
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Cliente cliente, BindingResult result) {
		
		if (result.hasErrors()) {
			return "clientes/editarPerfil";
		} else {
			this.clienteService.editar(cliente, clienteService.obtenerIdSesion());
			return "redirect:/clientes/perfil";
		}
	}
	
	@GetMapping()
	 public String listadoCliente(ModelMap modelMap) {
		String vista = "moderadores/listadoClientes";
		Iterable<Cliente> clientes = clienteService.findAllClient();
		Iterable<Vendedor> vendedores = vendedorService.findAllSeller();

		modelMap.addAttribute("clientes",clientes);
		modelMap.addAttribute("vendedores",vendedores);
		return vista;
	}


}
