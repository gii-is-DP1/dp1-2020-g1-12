package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Moderador;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ModeradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderadores/{moderadorId}")
public class ModeradorController {
	
	@Autowired
	private ModeradorService moderadorService;
	
	
	
	@GetMapping()
	public String mostrarPerfil(@PathVariable("moderadorId") Integer moderadorId, ModelMap modelMap){
		
		String  perfil="moderadores/perfil";
		Optional<Moderador> optperfil = moderadorService.datosPerfil(moderadorId);
		
		modelMap.addAttribute("moderador",optperfil.get());
		return perfil;
	}
	
	public String salvarPerfil() {
		String perfil = "moderadores/salvarPerfil";
		
		return perfil;
	}
    
	public String guardarPerfil(@Valid Moderador moderador,BindingResult result, ModelMap modelMap) {
		
		String vista="moderadores/perfil";
		if(result.hasErrors()) {
			modelMap.addAttribute("moderador", moderador);
			return "moderadores/editarPerfil";
		}else {
			moderadorService.guardar(moderador);
			modelMap.addAttribute("mensage", "El moderador ha sido guardado con éxito.");			
		}
		return vista;
	}
	
	@GetMapping(value = "/editar")
	public String editar(@PathVariable("moderadorId") int moderadorId, Model model) {
		Moderador moderador = this.moderadorService.findModeradorById(moderadorId);
		model.addAttribute(moderador);
		return "moderadores/editarPerfil";
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Moderador moderador, BindingResult result,
			@PathVariable("moderadorId") int moderadorId) {
		if (result.hasErrors()) {
			return "moderadores/editarPerfil";
		}
		else {
			moderador.setId(moderadorId);
			this.moderadorService.guardar(moderador);
			return "redirect:/moderadores/{moderadorId}";
		}
	}
	


}
