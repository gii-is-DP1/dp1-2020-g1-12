package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.service.BloqueoService;
import org.springframework.samples.dpc.service.exceptions.BloquearSinDescripcionException;
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
		String vista;
		try {
			this.bloqueoService.editar(bloqueo, bloqueoId, true);
			vista = "redirect:/clientes";
		} catch (BloquearSinDescripcionException e) {
            result.rejectValue("descripcion", "errónea", "La descripción debe estar entre 10 y 200 caractéres");
			vista = "moderadores/editarBloqueo";
		}
		return vista;
	}

	@GetMapping(value = "/desbloquear/{bloqueoId}")
	public String procesoDesbloquear(@Valid Bloqueo bloqueo, BindingResult result,
			@PathVariable("bloqueoId") int bloqueoId) {
		try {
			this.bloqueoService.editar(bloqueo, bloqueoId, false);
		} catch (BloquearSinDescripcionException e) {
			e.printStackTrace();
		}
		return "redirect:/clientes";
	}
}
