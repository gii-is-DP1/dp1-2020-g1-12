package org.springframework.samples.dpc.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.LineaPedidoService;
import org.springframework.samples.dpc.service.MensajeService;
import org.springframework.samples.dpc.service.SolicitudService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoValidaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaParecidaUsuarioException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/vendedores")
public class VendedorController {

	private final VendedorService vendedorService;
	private final ArticuloService articuloService;
	private final ClienteService clienteService;
	private final SolicitudService solicitudService;
	private final LineaPedidoService lineaPedidoService;
	private final MensajeService mensajeService;
	
	private static final String editPerfil = "vendedores/editarPerfil";

	@Autowired
	public VendedorController(VendedorService vendedorService, ArticuloService articuloService,
			ClienteService clienteService, SolicitudService solicitudService, LineaPedidoService lineaPedidoService,
			MensajeService mensajeService) {
		this.vendedorService = vendedorService;
		this.articuloService = articuloService;
		this.clienteService = clienteService;
		this.solicitudService = solicitudService;
		this.lineaPedidoService = lineaPedidoService;
		this.mensajeService = mensajeService;
	}

	@GetMapping(value = "/perfil")
	public String mostrarPerfil(ModelMap modelMap) {
		log.info("Entrando en la función Mostrar Perfil del controlador de Vendedor.");

		String perfil = "vendedores/perfil";
		Vendedor optperfil = vendedorService.getVendedorDeSesion();

		modelMap.addAttribute("vendedor", optperfil);
		return perfil;
	}

	@GetMapping(value = "/editar")
	public String editar(ModelMap model) {
		log.info("Entrando en la función Editar Perfil del controlador de Vendedor.");

		Vendedor vendedor = vendedorService.getVendedorDeSesion();
		User user = new User();
		vendedor.setUser(user);
		model.addAttribute(vendedor);
		return editPerfil;
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Vendedor vendedor, BindingResult result,ModelMap model) throws Exception {
		log.info("Entrando en la función Proceso Editar Perfil del controlador de Vendedor.");

		if(!vendedor.getVersion().equals(vendedorService.getVendedorDeSesion().getVersion())) {
			model.put("message", "Este perfil está siendo editado de forma concurrente, vuelva a intentarlo.");
			return editar(model);
		}
		
		if (result.hasErrors()) {
			return editPerfil;
		} else {
			try {
				this.vendedorService.editar(vendedor, vendedorService.obtenerIdSesion());
				return "redirect:/vendedores/perfil";				
			}catch (ContrasenyaNoValidaException e) {
				log.warn("La función Proceso Editar Perfil ha lanzado la excepción Contrasenya No Válida");

				model.put("message", "La contraseña introducida no es válida. Debe contener entre 8 y 16 caracteres y al menos una mayúscula, una minúscula y un dígito.");
				return editPerfil;
			}catch(ContrasenyaNecesariaException e) {
				log.warn("La función Proceso Editar Perfil ha tenido un error relacionado con la contraseña.");

				model.put("message", "Si quieres editar tu contaseña debes de introducir tu antigua contraseña.");
	            return editPerfil;
			}catch(ContrasenyaNoCoincideException e) {
				log.warn("La función Proceso Editar Perfil ha tenido un error debido a que las contraseña no coinciden.");

				model.put("message", "La contraseña introducida no coincide con la de la cuenta.");
	            return editPerfil;
			}catch(ContrasenyaParecidaUsuarioException e) {
				log.warn("La función Proceso Formulario de Cliente ha lanzado la excepción Contrasenya Parecida Usuario.");
				
				model.put("message", "La contraseña no puede ser idéntica al nombre de usuario.");
				return editPerfil;
			}

		}
	}

	@GetMapping(value = "/perfilCliente/{clienteId}")
	public String mostrarPerfilCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
		log.info("Entrando en la función Mostrar Perfil del Comprador del controlador de Vendedor.");

		Cliente cliente = clienteService.findClientById(clienteId);
		modelMap.addAttribute("cliente", cliente);
		modelMap.remove(cliente.getDni());
		return "vendedores/perfilCliente";
	}

	@GetMapping(value = "/articulosEnVenta")
	public String mostrarArticulosEnVenta(
			@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, ModelMap modelMap) {
		log.info("Entrando en la función Mostrar Artículos en Venta del controlador de Vendedor.");

		String vista = "vendedores/listadoArticulos";
		Page<Articulo> articulos = articuloService.articulosEnVentaByProvider(vendedorService.obtenerIdSesion(), page,
				size, orden);
		
		// Guardo el parámetro de ordenación para que al cambiar de página se siga usando el filtro seleccionado
		String signo = articulos.getSort().get().findAny().get().isAscending() ? "" : "-"; 
		String ordenacion = signo + articulos.getSort().get().findAny().get().getProperty(); 

		modelMap.addAttribute("articulos", articulos);
		modelMap.addAttribute("ordenacion", ordenacion);
		return vista;
	}

	@GetMapping(value = "/listadoSolicitudes")
	public String mostrarListadoSolicitudes(
			@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, ModelMap modelMap) {
		log.info("Entrando en la función Mostrar Listado de Solicitudes del controlador de Vendedor.");

		Page<Solicitud> solicitudes = solicitudService.getsolicitudesByProvider(vendedorService.obtenerIdSesion(), page,
				size, orden);
		// Guardo el parámetro de ordenación para que al cambiar de página se siga usando el filtro seleccionado
		String signo = solicitudes.getSort().get().findAny().get().isAscending() ? "" : "-";
		String ordenacion = signo + solicitudes.getSort().get().findAny().get().getProperty();

		modelMap.addAttribute("solicitudes", solicitudes);
		modelMap.addAttribute("ordenacion", ordenacion);
		return "vendedores/listadoSolicitudes";
	}

	@GetMapping(value = "/articulo/{articuloId}")
	public String mostrarArticuloDetallado(@PathVariable("articuloId") int articuloId, ModelMap modelMap) {
		log.info("Entrando en la función Mostrar un Artículo del controlador de Vendedor.");

		String vista;
		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(articuloId);
		if (vendedor != null && vendedor.getId().equals(vendedorService.obtenerIdSesion())) {
			vista = "vendedores/articulo";
			Articulo articulo = articuloService.findArticuloById(articuloId);
			modelMap.addAttribute("articulo", articulo);
		} else {
			vista = "redirect:/vendedores/articulosEnVenta";
		}
		return vista;
	}

	@GetMapping(value = "/solicitud/{solicitudId}")
	public String mostrarSolicitudDetallada(@PathVariable("solicitudId") int solicitudId, ModelMap modelMap) {
		log.info("Entrando en la función Mostrar una Solicitud del controlador de Vendedor.");

		String vista;
		Solicitud solicitud = solicitudService.detallesSolicitud(solicitudId);
		if (solicitud != null && solicitud.getVendedor().getId().equals(vendedorService.obtenerIdSesion())) {
			vista = "vendedores/solicitud";
			modelMap.addAttribute("solicitud", solicitud);
		} else {
			vista = "redirect:/vendedores/listadoSolicitudes";
		}
		return vista;
	}

	@GetMapping(value = "/eliminarArticulo/{articuloId}")
	public String eliminarArticulo(@PathVariable("articuloId") int articuloId, ModelMap modelMap) {
		log.info("Entrando en la función Dar de Baja un Artículo del controlador de Vendedor.");

		Vendedor vendedor = vendedorService.vendedorDeUnArticulo(articuloId);
		if (vendedor != null && vendedor.getId().equals(vendedorService.obtenerIdSesion())) {
			articuloService.eliminarArticulo(articuloId);
			Articulo articulo = articuloService.findArticuloById(articuloId);
			modelMap.addAttribute("articulo", articulo);
		}
		return "redirect:/vendedores/articulosEnVenta";
	}

	@GetMapping(value = "/eliminarSolicitud/{solicitudId}")
	public String eliminarSolicitud(@PathVariable("solicitudId") int solicitudId) {
		log.info("Entrando en la función Cancelar una Solicitud del controlador de Vendedor.");

		Solicitud solicitud = solicitudService.detallesSolicitud(solicitudId);

		if (solicitud != null && solicitud.getSituacion().equals(Situacion.Pendiente)
				&& solicitud.getVendedor().equals(vendedorService.getVendedorDeSesion())) {
			solicitudService.eliminarSolicitud(solicitud);
		}
		return "redirect:/vendedores/listadoSolicitudes";
	}

	@GetMapping(value = "/articulosVendidos")
	public String mostrarArticulosVendidos(
			@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "orderBy", defaultValue = "-id", required = false) String orden, ModelMap modelMap) {
		log.info("Entrando en la función Mostrar Artículos Vendidos del controlador de Vendedor.");

		String vista = "vendedores/listadoArticulosVendidos";
		Page<LineaPedido> optarticulos = lineaPedidoService
				.articulosVendidosByProvider(page, size, orden, vendedorService.obtenerIdSesion());
		
		// Guardo el parámetro de ordenación para que al cambiar de página se siga usando el filtro seleccionado
		String signo = optarticulos.getSort().get().findAny().get().isAscending() ? "" : "-";
		String ordenacion = signo + optarticulos.getSort().get().findAny().get().getProperty();
		if(optarticulos!=null) {
			List<Integer> contadores = mensajeService.getMensajesNoLeidosVendedor(optarticulos.getContent());
			modelMap.addAttribute("contadores", contadores);
		}
		modelMap.addAttribute("lineaPedido", optarticulos);
		modelMap.addAttribute("ordenacion", ordenacion);
		return vista;
	}

}
