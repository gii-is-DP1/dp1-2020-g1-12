package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Moderador;
import org.springframework.samples.petclinic.service.ModeradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderadores")
public class ModeradorController {
	
	private final ModeradorService moderadorService;
	
	@Autowired
	public ModeradorController(ModeradorService moderadorService) {
		this.moderadorService = moderadorService;
	}

	@GetMapping("/perfil")
	public String mostrarPerfil(ModelMap modelMap){
		
		String  perfil="moderadores/perfil";
		Optional<Moderador> optperfil = moderadorService.datosPerfil(moderadorService.obtenerIdSesion());
		
		modelMap.addAttribute("moderador",optperfil.get());
		return perfil;
	}
		
	@GetMapping(value = "/editar")
	public String editar(Model model) {
		Moderador moderador = this.moderadorService.findModeradorById(moderadorService.obtenerIdSesion());
		model.addAttribute(moderador);
		return "moderadores/editarPerfil";
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Moderador moderador, BindingResult result) {
		if (result.hasErrors()) {
			return "moderadores/editarPerfil";
		}
		else {
			this.moderadorService.editar(moderador, moderadorService.obtenerIdSesion());
			return "redirect:/moderadores/perfil";
		}
	}
	


}
