package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {
	
	
	private final SolicitudService solicitudService;
	
	@Autowired
	public SolicitudController(SolicitudService solicitudService) {
		this.solicitudService = solicitudService;
	}
	
	@GetMapping()
	public String listadoSolicitud(ModelMap modelMap) {
		String vista = "solicitudes/listadoSolicitudes";

		Iterable<Solicitud> solicitudes = solicitudService.solicitudesPendientes();
		modelMap.addAttribute("solicitudes",solicitudes);
		return vista;
	}
	
	@GetMapping(value="/{solicitudId}")
	public String mostrarSolicitud(@PathVariable("solicitudId") Integer solicitudId, ModelMap modelMap) {
		String solicitud="solicitudes/detalles";
		Optional<Solicitud> optsolicitud = solicitudService.detallesSolicitud(solicitudId);
		modelMap.addAttribute("solicitud", optsolicitud.get());
		return solicitud;
	}
	
	@GetMapping(value="/{solicitudId}/aceptar")
	public String aceptarSolicitud(@PathVariable("solicitudId") Integer solicitudId, ModelMap modelMap) {
		solicitudService.aceptarSolicitud(solicitudId);
		modelMap.addAttribute("mensaje", "La solicitud ha sido aceptada correctamente");
		return listadoSolicitud(modelMap);
	}
	
	@GetMapping(path="/new")
	public String crearSolicutud(ModelMap modelMap) {
		String vista = "solicitudes/editarSolicitud";
		modelMap.addAttribute("solicitud",new Solicitud());
		return vista;
	}
	
	@PostMapping(path = "/save")
	public String guardarSolicitud(@Valid Solicitud solicitud,BindingResult result,ModelMap modelMap) {
		String vista = "solicitudes/listadoSolicitud";
		if(result.hasErrors()) {
			modelMap.addAttribute("solicitud",solicitud);
			return "solicitudes/editarSolicitud";
		}else {
			solicitudService.guardar(solicitud);
			modelMap.addAttribute("mensaje", "Se ha guardado correctamente.");
			vista =listadoSolicitud(modelMap); 
		}
		return vista;
	}

}
