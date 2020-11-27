package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.service.BloqueoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bloqueos")
public class BloqueoController {
	
	private final BloqueoService bloqueoService;
	
	@Autowired
	public BloqueoController(BloqueoService bloqueoService) {
		this.bloqueoService = bloqueoService;
	}

	@GetMapping(value = "/{bloqueoId}")
	public String editar(@PathVariable("bloqueoId") int bloqueoId, Model model) {
		Bloqueo bloqueo = this.bloqueoService.findBlockById(bloqueoId);
		model.addAttribute(bloqueo);
		return "moderadores/editarBloqueo";
	}

	@PostMapping(value = "/{bloqueoId}")
	public String procesoBloquear(@Valid Bloqueo bloqueo, BindingResult result,
			@PathVariable("bloqueoId") int bloqueoId) {
		if (result.hasErrors()) {
			return "moderadores/editarBloqueo";
		}
		else {
			this.bloqueoService.editar(bloqueo, bloqueoId, true);
			return "redirect:/clientes";
		}
	}
	
	@GetMapping(value = "/desbloquear/{bloqueoId}")
	public String procesoDesbloquear(@Valid Bloqueo bloqueo, BindingResult result,
			@PathVariable("bloqueoId") int bloqueoId) {
		this.bloqueoService.editar(bloqueo, bloqueoId, false);
		return "redirect:/clientes";
		}
}
