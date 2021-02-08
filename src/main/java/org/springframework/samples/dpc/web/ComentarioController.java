package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.samples.dpc.service.exceptions.ComentarioProhibidoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/comentario")
public class ComentarioController {

	private final ComentarioService comentarioService;
	private static final String editCommentView = "articulos/editarComentario";

	@Autowired
	public ComentarioController(ComentarioService comentarioService) {
		this.comentarioService = comentarioService;
	}

	@GetMapping(value = "/articulo/{articuloId}")
	public String crearComentario(@PathVariable("articuloId") int articuloId, Model model) {
		log.info("Entrando en la función Crear un Comentario del controlador de Comentario.");

		model.addAttribute("comentario", new Comentario());
		model.addAttribute("articulo", articuloId);
		return editCommentView;
	}

	@PostMapping(value = "/articulo/{articuloId}")
	public String procesoComentar(@Valid Comentario comentario, BindingResult result,
			@PathVariable("articuloId") int articuloId, Model model) {
		log.info("Entrando en la función Proceso Crear un Comentario del controlador de Comentario.");

		String vista;
		if (result.hasErrors()) {
			model.addAttribute("comentario", comentario);
			model.addAttribute("articulo", articuloId);
			if (result.getFieldError("valoracion") != null) {
				model.addAttribute("errores", result.getFieldError("valoracion").getDefaultMessage());
			}
			vista = editCommentView;
		} else {
			try {
				this.comentarioService.guardarComentario(comentario, articuloId);
			} catch (ComentarioProhibidoException e) {
				log.warn("La función Proceso Crear un Comentario ha lanzado la excepción ComentarioProhibido.");

				result.rejectValue("descripcion", "errónea", "No puedes publicar un comentario si no "
						+ "eres el vendedor del artículo o no lo has comprado previamente.");
				return editCommentView;
			}
			vista = "redirect:/articulos/{articuloId}";
		}
		return vista;
	}

	@GetMapping(value = "/eliminar/{comentarioId}/articulo/{articuloId}")
	public String borrarComentario(@PathVariable("comentarioId") int comentarioId,
			@PathVariable("articuloId") int articuloId, Model model) {
		log.info("Entrando en la función Borrar un Comentario del controlador de Comentario.");

		Comentario comentario = comentarioService.findCommentById(comentarioId);
		if (comentario != null && comentario.getArticulo().getId().equals(articuloId)) {
			comentarioService.eliminarComentario(comentario);
		}
		return "redirect:/articulos/{articuloId}";
	}

	@GetMapping(value = "/editar/{comentarioId}/articulo/{articuloId}")
	public String editarComentario(@PathVariable("comentarioId") int comentarioId,
			@PathVariable("articuloId") int articuloId, Model model) {
		log.info("Entrando en la función Editar un Comentario del controlador de Comentario.");
		Comentario comentario = comentarioService.findCommentById(comentarioId);
		model.addAttribute("comentario",comentario);
		model.addAttribute("articulo", articuloId);
		return editCommentView;
	}

	@PostMapping(value = "/editar/{comentarioId}/articulo/{articuloId}")
	public String procesoEditarComentario(Comentario comentario, 
			@PathVariable("comentarioId") int comentarioId, @PathVariable("articuloId") int articuloId,
			ModelMap model, BindingResult result) {
		log.info("Entrando en la función Proceso Editar un Comentario del controlador de Comentario.");
		
		if (comentario.getDescripcion() == null || comentario.getDescripcion().length() < 11 || comentario.getValoracion() == null || comentario.getValoracion() < 0 || comentario.getValoracion() > 6) {
			comentario.setId(comentarioId);
			model.addAttribute("comentario", comentario);
			model.addAttribute("articulo", articuloId);
			model.put("message", "Todos los campos son obligatorios, la descripción debe estar entre 10 y 200 caracteres");
			return editCommentView;
		} else {
			try {
				this.comentarioService.editar(comentario, comentarioId, articuloId);
				return "redirect:/articulos/{articuloId}";
			} catch (ComentarioProhibidoException e) {
				log.warn("La función Proceso Editar un Comentario ha lanzado la excepción ComentarioProhibido.");

				result.rejectValue("descripcion", "errónea", "No puedes publicar un comentario si no "
						+ "eres el vendedor del artículo o no lo has comprado previamente.");
				return editCommentView;
			}
		}
	}
}
