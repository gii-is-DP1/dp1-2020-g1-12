package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Vendedor;
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
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VendedorService vendedorService;
	
	@GetMapping(value="/{clienteId}")
	public String mostrarPerfil(@PathVariable("clienteId") Integer clienteId, ModelMap modelMap){
		
		String  perfil="clientes/perfil";
		Optional<Cliente> optperfil = clienteService.datosPerfil(clienteId);		
		modelMap.addAttribute("cliente",optperfil.get());
		return perfil;
	}
	
	public String salvarPerfil() {
		String perfil = "clientes/salvarPerfil";
		
		return perfil;
	}
    
	public String guardarPerfil(@Valid Cliente cliente,BindingResult result, ModelMap modelMap) {
		String vista="clientes/perfil";
		if(result.hasErrors()) {
			modelMap.addAttribute("cliente", cliente);
			return "cliente/editarPerfil";
		}else {
			clienteService.guardar(cliente);
			modelMap.addAttribute("mensage", "El cliente ha sido guardado con éxito.");			
		}
		return vista;
	}
	
	@GetMapping(value = "/{clienteId}/editar")
	public String editar(@PathVariable("clienteId") int clienteId, Model model) {
		Cliente cliente = this.clienteService.findClientById(clienteId);
		model.addAttribute(cliente);
		return "clientes/editarPerfil";
	}

	@PostMapping(value = "/{clienteId}/editar")
	public String procesoEditar(@Valid Cliente cliente, BindingResult result,
			@PathVariable("clienteId") int clienteId) {
		if (result.hasErrors()) {
			return "clientes/editarPerfil";
		}
		else {
			this.clienteService.editar(cliente, clienteId);
			return "redirect:/clientes/{clienteId}";
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
