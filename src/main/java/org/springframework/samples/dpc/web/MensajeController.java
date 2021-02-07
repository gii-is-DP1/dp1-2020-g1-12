package org.springframework.samples.dpc.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.service.MensajeService;
import org.springframework.samples.dpc.service.exceptions.MensajeProhibidoException;
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
@RequestMapping("/chat")
public class MensajeController {

	private final MensajeService mensajeService;
	private static final String URLChat = "redirect:/chat/{rol}/{id}";

	@Autowired
	public MensajeController(MensajeService mensajeService) {
		this.mensajeService = mensajeService;
	}

	@GetMapping("/{rol}/{id}")
	public String visualizarChat(@PathVariable("rol") String rol, @PathVariable("id") int id, Model model) {
		log.info("Entrando en la función Visualizar Chat del controlador de Mensaje.");
		// La id es la de un artículo si venimos de un cliente o la de un cliente si
		// venimos de un vendedor
		Pair<List<Mensaje>, List<String>> mensajes;
		try {
			mensajes = mensajeService.obtenerMensajes(rol, id);
			model.addAttribute("dni", mensajes.getSecond().get(0));
			model.addAttribute("nombre", mensajes.getSecond().get(1));
			model.addAttribute("receptorId", mensajes.getSecond().get(2));
			model.addAttribute("id", id);
			model.addAttribute("mensajes", mensajes.getFirst());
			model.addAttribute("nuevoMensaje", new Mensaje());
		} catch (MensajeProhibidoException e) {
			log.warn("La función Visualizar Chat ha lanzado la excepción MensajeProhibido.");
			return "redirect:/";
		}
		return "users/chat";
	}

	@PostMapping("/{rol}/{receptorId}/{id}")
	public String enviarMensaje(@Valid Mensaje mensaje, @PathVariable("rol") String rol,
			@PathVariable("receptorId") Integer receptorId, @PathVariable("id") int id, Model model,
			BindingResult result) {
		log.info("Entrando en la función Enviar Mensaje del controlador de Mensaje.");
		if (result.hasErrors() || mensaje.getTexto().trim().isEmpty()) {
			return URLChat;
		}
		try {
			mensajeService.enviarMensaje(mensaje, rol, receptorId, id);
		} catch (MensajeProhibidoException e) {
			log.warn("La función Enviar Mensaje ha lanzado la excepción MensajeProhibido.");
			return URLChat;
		}

		return URLChat;
	}
}
