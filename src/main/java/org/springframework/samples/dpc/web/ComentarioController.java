package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.samples.dpc.service.exceptions.ComentarioProhibidoException;
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

	
	@Autowired
	public ComentarioController(ComentarioService comentarioService) {
		this.comentarioService= comentarioService;
	}
	
	@GetMapping(value = "/articulo/{articuloId}")
	public String crearComentario(@PathVariable("articuloId") int articuloId, Model model) {
		model.addAttribute("comentario", new Comentario());
		model.addAttribute("articulo", articuloId);
		return "articulos/editarComentario";
	}

	@PostMapping(value = "/articulo/{articuloId}")
	public String procesoComentar(@Valid Comentario comentario, BindingResult result,
			@PathVariable("articuloId") int articuloId, Model model) {
		String vista;
		if (result.hasErrors()) {
			model.addAttribute("comentario",comentario);
			vista = "articulos/editarComentario";
		} else {
			try {
				this.comentarioService.guardarComentario(comentario, articuloId);
			} catch (ComentarioProhibidoException e) {
	            result.rejectValue("descripcion", "errónea", "No puedes publicar un comentario si no "
	            		+ "eres el propietario del artículo");
				return "articulos/editarComentario";
			}
			vista = "redirect:/articulos/{articuloId}";
		}
		return vista;
	}
	
	@GetMapping(value = "/eliminar/{comentarioId}/articulo/{articuloId}")
	public String borrarGenero(@PathVariable("comentarioId") int comentarioId, 
			@PathVariable("articuloId") int articuloId, Model model) {
		Comentario comentario =  comentarioService.findCommentById(comentarioId);
		if(comentario != null && comentario.getArticulo().getId().equals(articuloId)) {
			comentarioService.eliminarComentario(comentario);
		}
		return "redirect:/articulos/{articuloId}";
	}
}
