package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comentario")
public class ComentarioController {
	
	private final ComentarioService comentarioService;
	private final ClienteService clienteService;


	@Autowired
	public ComentarioController(ComentarioService comentarioService, ClienteService clienteService) {
		this.comentarioService= comentarioService;
		this.clienteService = clienteService;
	}
	
	@GetMapping(value = "/articulo/{articuloId}")
	public String crearComentario(@PathVariable("articuloId") int articuloId, Model model) {
		model.addAttribute("comentario", new Comentario());
		model.addAttribute("clienteId", clienteService.obtenerIdSesion());
		model.addAttribute("articulo", articuloId);
		return "articulos/editarComentario";
	}

	@PostMapping(value = "/articulo/{articuloId}")
	public String procesoComentar(@Valid Comentario comentario, BindingResult result,
			@PathVariable("articuloId") int articuloId, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("comentario",comentario);
			return "articulos/editarComentario";
		} else {
			this.comentarioService.guardarComentario(comentario, clienteService.findClientById(clienteService.obtenerIdSesion()), articuloId);
			return "redirect:/articulos/{articuloId}";
		}
	}
}
