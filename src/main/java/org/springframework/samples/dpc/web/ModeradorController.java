package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Moderador;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.service.ModeradorService;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/moderadores")
public class ModeradorController {
	
	private final ModeradorService moderadorService;
	private static final String editPerfil = "moderadores/editarPerfil";
	
	@Autowired
	public ModeradorController(ModeradorService moderadorService) {
		this.moderadorService = moderadorService;
	}

	@GetMapping("/perfil")
	public String mostrarPerfil(ModelMap modelMap){
		log.info("Entrando en la función Mostrar Perfil del controlador de Moderador.");

		String vista ="moderadores/perfil";
		Moderador perfil = moderadorService.getModeradorDeSesion();
		
		modelMap.addAttribute("moderador", perfil);
		return vista;
	}
		
	@GetMapping(value = "/editar")
	public String editar(ModelMap model) {
		log.info("Entrando en la función Editar Perfil del controlador de Moderador.");

		Moderador moderador = moderadorService.getModeradorDeSesion();
		User user = new User();
		moderador.setUser(user);
		model.addAttribute(moderador);
		return editPerfil;
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Moderador moderador, BindingResult result,ModelMap model) throws Exception {
		log.info("Entrando en la función Proceso Editar Perfil del controlador de Moderador.");

		if(!moderador.getVersion().equals(moderadorService.getModeradorDeSesion().getVersion())) {
			model.put("message", "Este perfil está siendo editado de forma concurrente, vuelva a intentarlo.");
			return editar(model);
		}
		
		if (result.hasErrors()) {
			return editPerfil;
		} else {
			try {
				this.moderadorService.editar(moderador, moderadorService.obtenerIdSesion());
				return "redirect:/moderadores/perfil";
			} catch(ContrasenyaNecesariaException e) {
				log.warn("La función Proceso Editar Perfil ha tenido un error relacionado con la contraseña.");
				
				result.rejectValue("user.username", "errónea", "Si quieres editar tu contaseña debes de introducir tu antigua contraseña.");
				return editPerfil;
			} catch(ContrasenyaNoCoincideException e) {
				log.warn("La función Proceso Editar Perfil ha tenido un error debido a que las contraseñas no coinciden.");
				
				result.rejectValue("user.password", "erronea", "La contraseña introducida no coincide con la de la cuenta.");
				return editPerfil;
			}
		}
	}
}
