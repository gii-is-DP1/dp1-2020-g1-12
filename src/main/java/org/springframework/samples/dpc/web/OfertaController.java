package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.service.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("vendedores/ofertas")
public class OfertaController {

	private final OfertaService ofertaService;
	
	@Autowired
	public OfertaController(OfertaService ofertaService) {
		this.ofertaService = ofertaService;
	}

	@GetMapping(value = "/{ofertaId}/articulo/{articuloId}")
	public String editar(@PathVariable("ofertaId") int ofertaId, ModelMap model) {
		log.info("Entrando en la función Editar Oferta del controlador de Oferta.");

		Oferta oferta = this.ofertaService.findOfertById(ofertaId);
		model.addAttribute(oferta);
		return "vendedores/editarOferta";
	}

	@PostMapping(value = "/{ofertaId}/articulo/{articuloId}")
	public String procesoOfertar(@Valid Oferta oferta, BindingResult result, @PathVariable("ofertaId") int ofertaId,
			@PathVariable("articuloId") int articuloId,ModelMap modelMap) {
		log.info("Entrando en la función Proceso Editar Oferta del controlador de Oferta.");

		if(!oferta.getVersion().equals(ofertaService.findOfertById(ofertaId).getVersion())) {
			modelMap.put("message", "Se ha creado una oferta recientemente, vuelve a intentarlo.");
			return editar(ofertaId,modelMap);
		}
		if (result.hasErrors()) {
			return "vendedores/editarOferta";
		} else {
			this.ofertaService.editar(oferta, ofertaId, true);	
			return "redirect:/vendedores/articulo/{articuloId}";
		}
	}
	
	  @PostMapping(value = "/desofertar/{ofertaId}/articulo/{articuloId}") 
	  public String procesoDesofertar(@Valid Oferta oferta, BindingResult result,
			  @PathVariable("ofertaId") int ofertaId, @PathVariable("articuloId") int articuloId,ModelMap modelMap) {
			log.info("Entrando en la función Eliminar Oferta del controlador de Oferta.");
			
			if(!oferta.getVersion().equals(ofertaService.findOfertById(ofertaId).getVersion())) {
				modelMap.put("message", "Se ha creado una oferta recientemente, para eliminarla vuelve a intentarlo.");
				return editar(ofertaId,modelMap);
			}
		  this.ofertaService.editar(oferta, ofertaId, false); 
		  return "redirect:/vendedores/articulo/{articuloId}";
	  }
	 
}
