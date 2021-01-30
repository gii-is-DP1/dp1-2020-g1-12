package org.springframework.samples.dpc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.service.MensajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/chat")
public class MensajeController {

	private final MensajeService mensajeService;

	@Autowired
	public MensajeController(MensajeService mensajeService) {
		this.mensajeService = mensajeService;
	}

	@GetMapping("/{rol}/{id}")
	public String visualizarChat(@PathVariable("rol") String rol, @PathVariable("id") int id,
			Model model) {
		log.info("Entrando en la función Visualizar Chat del controlador de Mensaje.");

		if(!rol.equals("vendedor") && !rol.equals("cliente")) {
			return "redirect:/";
		}
		else {
			//La id es la de un artículo si venimos de un cliente o la de un cliente si venimos de un vendedor
			Pair<List<Mensaje>, List<String>> mensajes = mensajeService.obtenerMensajes(rol, id);
			if(rol.equals("cliente")) {
				model.addAttribute("dni", mensajes.getSecond().get(0));
				model.addAttribute("nombre", mensajes.getSecond().get(1));
			}
			else {
				model.addAttribute("dni", mensajes.getSecond().get(0));
				model.addAttribute("nombre", mensajes.getSecond().get(1));
			}
			model.addAttribute("mensajes", mensajes.getFirst());
		}
		return "users/chat";
	}
}
