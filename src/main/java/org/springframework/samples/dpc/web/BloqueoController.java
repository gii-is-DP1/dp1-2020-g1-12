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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("Entrando en la función Editar un Bloqueo del controlador de Bloqueo.");

		Bloqueo bloqueo = this.bloqueoService.findBlockById(bloqueoId);
		model.addAttribute(bloqueo);
		return "moderadores/editarBloqueo";
	}

	@PostMapping(value = "/{bloqueoId}")
	public String procesoBloquear(@Valid Bloqueo bloqueo, BindingResult result,
			@PathVariable("bloqueoId") int bloqueoId) {
		log.info("Entrando en la función Proceso Bloquear del controlador de Bloqueo.");

		String vista;
		if(!bloqueoService.findBlockById(bloqueoId).getVersion().equals(bloqueo.getVersion())) {
			return "moderadores/editarBloqueo";
		}
		try {
			this.bloqueoService.editar(bloqueo, bloqueoId, true);
			vista = "redirect:/clientes";
		} catch (BloquearSinDescripcionException e) {
			log.warn("La función Proceso Bloquear ha lanzado la excepción BloquearSinDescpción.");

            result.rejectValue("descripcion", "errónea", "La descripción debe estar entre 10 y 200 caractéres");
			vista = "moderadores/editarBloqueo";
		}
		return vista;
	}

	@GetMapping(value = "/desbloquear/{bloqueoId}")
	public String procesoDesbloquear(@Valid Bloqueo bloqueo, BindingResult result,
			@PathVariable("bloqueoId") int bloqueoId) {
		log.info("Entrando en la función Proceso Desbloquear del controlador de Bloqueo.");

		try {
			this.bloqueoService.editar(bloqueo, bloqueoId, false);
		} catch (BloquearSinDescripcionException e) {
			log.error("La función Proceso Desbloquear ha lanzado la excepción BloquearSinDescripción.");
		}
		return "redirect:/clientes";
	}
}
