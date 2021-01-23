package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.service.TarjetaService;
import org.springframework.samples.dpc.util.TarjetaValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/tarjetas")
public class TarjetaController {
	
	private final TarjetaService tarjetaService;

	@Autowired
	public TarjetaController(TarjetaService tarjetaService) {
		this.tarjetaService = tarjetaService;
	}
	
	@InitBinder("tarjetaCredito")
	public void initTarjetaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new TarjetaValidator());
	}
	
	@GetMapping(value = "/new")
	public String iniciarFormulario(Model model) {
		log.info("Entrando en la función Iniciar Formulario del controlador de Tarjeta de Crédito.");

		model.addAttribute("tarjeta", new TarjetaCredito());
		return "clientes/editarTarjeta";
	}
	
	@PostMapping(path = "/save")
	public String guardarTarjeta(@Valid TarjetaCredito tarjetaCredito, BindingResult result,ModelMap modelMap) {
		log.info("Entrando en la función Guardar Tarjeta de Crédito del controlador de Tarjeta de Crédito.");

		String vista = "redirect:/clientes/perfil";
		if(result.hasErrors()) {
			modelMap.addAttribute("tarjeta", tarjetaCredito);
			modelMap.addAttribute("errores", result.getAllErrors());
			return "clientes/editarTarjeta";
		} else {
			tarjetaService.anyadirTarjeta(tarjetaCredito);
			return vista;
		}
	}
	
	@GetMapping(value = "/{tarjetaId}/delete")
	public String borrarTarjetaPersona(@PathVariable("tarjetaId") int tarjetaId,Model model) {
		log.info("Entrando en la función Borrar Tarjeta de Crédito del controlador de Tarjeta de Crédito.");

		if(tarjetaService.findTarjetaById(tarjetaId) != null) {
			tarjetaService.eliminarTarjetaPersona(tarjetaId);
		}
		return "redirect:/clientes/perfil";
	}
}
