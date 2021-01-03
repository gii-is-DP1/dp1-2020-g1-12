package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.SolicitudService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.samples.dpc.service.exceptions.PrecioMenorAlEnvioException;
import org.springframework.samples.dpc.service.exceptions.SolicitudRechazadaSinRespuestaException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {
	
	private final SolicitudService solicitudService;
	private final VendedorService vendedorService;
	private static final String sol = "solicitud";
	private static final String mensaje = "mensaje";
	private static final String editApplicationView = "solicitudes/editarSolicitud";
	
	@Autowired
	public SolicitudController(SolicitudService solicitudService, VendedorService vendedorService) {
		this.solicitudService = solicitudService;
		this.vendedorService = vendedorService;
	}
	
	@GetMapping()
	public String listadoSolicitud(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, 
			ModelMap modelMap) {
		String vista = "solicitudes/listadoSolicitudes";

		Page<Solicitud> solicitudes = solicitudService.solicitudesPendientes(page, size, orden);
		String signo = solicitudes.getSort().get().findAny().get().isAscending() ? "" : "-";		//Guardo el parámetro de ordenación para que al cambiar
		String ordenacion = signo + solicitudes.getSort().get().findAny().get().getProperty();	//de página se siga usando el filtro seleccionado
		
		modelMap.addAttribute("solicitudes",solicitudes);
		modelMap.addAttribute("ordenacion", ordenacion);

		return vista;
	}
	
	@GetMapping(value="/{solicitudId}")
	public String mostrarSolicitud(@PathVariable("solicitudId") Integer solicitudId, ModelMap modelMap) {
		String vista="solicitudes/detalles";
		Solicitud solicitud = solicitudService.detallesSolicitud(solicitudId);
		modelMap.addAttribute(sol, solicitud);
		return vista;
	}
	
	@GetMapping(value="/{solicitudId}/aceptar")
	public String aceptarSolicitud(@PathVariable("solicitudId") Integer solicitudId, ModelMap modelMap) {
		solicitudService.aceptarSolicitud(solicitudId);
		modelMap.addAttribute(mensaje, "La solicitud ha sido aceptada correctamente");
		return listadoSolicitud(0, 10, "-id", modelMap);
	}
	
	@PostMapping(value="/{solicitudId}/denegar")
	public String denegarSolicitud(@PathVariable("solicitudId") Integer solicitudId,Solicitud solicitud, 
			ModelMap modelMap, BindingResult result) {
		try {
			solicitudService.denegarSolicitud(solicitudId,solicitud.getRespuesta());
			modelMap.addAttribute(mensaje, "La solicitud ha sido denegada correctamente");
		} catch(SolicitudRechazadaSinRespuestaException ex) {
			result.rejectValue("respuesta", "error", 
					"La respuesta es obligatoria al rechazar y debe tener un tamaño mayor de 15");
			modelMap.addAttribute(mensaje, "La respuesta es obligatoria al rechazar y debe tener un tamaño mayor de 15");
			return mostrarSolicitud(solicitudId, modelMap);
		}
		return listadoSolicitud(0, 10, "-id", modelMap);
	}
	
	@GetMapping(path="/new")
	public String crearSolicutud(ModelMap modelMap) {
		modelMap.addAttribute(sol, new Solicitud());
		modelMap.addAttribute("vendedorId", vendedorService.obtenerIdSesion());
		return editApplicationView;
	}
	
	@PostMapping(path = "/save")
	public String guardarSolicitud(@Valid Solicitud solicitud, BindingResult result,ModelMap modelMap) {
		String vista = "redirect:/vendedores/listadoSolicitudes";
		if(result.hasErrors()) {
			modelMap.addAttribute(sol,solicitud);
			return editApplicationView;
		} else {
			try {
			solicitudService.guardar(solicitud, vendedorService.findSellerById(vendedorService.obtenerIdSesion()));
			modelMap.addAttribute(mensaje, "Se ha guardado correctamente.");
			} catch(PrecioMenorAlEnvioException ex) {
				result.rejectValue("gastoEnvio", "error", 
						"Los gastos de envío deben ser inferiores al precio del artículo");
				return editApplicationView;
			}
		}
		return vista;
	}
	
	@GetMapping(value="/{solicitudId}/solicitante/{vendedorId}")
	public String perfilSolicitante(ModelMap modelMap, @PathVariable("vendedorId") Integer vendedorId, 
			@PathVariable("solicitudId") Integer solicitudId) {
		String solicitante="solicitudes/solicitante";
		Vendedor vendedor = vendedorService.findSellerById(vendedorId);
		modelMap.addAttribute("vendedor", vendedor);
		modelMap.addAttribute("solicitudId", solicitudId);
		return solicitante;
	}

}
