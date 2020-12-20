package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.service.TarjetaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tarjetas")
public class TarjetaController {
	
	private TarjetaService tarjetaService;
	
	@Autowired
	public TarjetaController(TarjetaService tarjetaService) {
		this.tarjetaService = tarjetaService;
	}
	
	@GetMapping(value = "/new")
	public String iniciarFormulario(Model model) {
		model.addAttribute("tarjeta", new TarjetaCredito());
		return "clientes/editarTarjeta";
	}
	
	@PostMapping(path = "/save")
	public String guardarTarjeta(@Valid TarjetaCredito tarjeta, BindingResult result,ModelMap modelMap) {
		String vista = "redirect:/clientes/perfil";
		if(result.hasErrors()) {
			modelMap.addAttribute("tarjeta", tarjeta);
			return "clientes/editarTarjeta";
		} else {
			tarjetaService.anyadirTarjeta(tarjeta);
			return vista;
		}
	}
}
