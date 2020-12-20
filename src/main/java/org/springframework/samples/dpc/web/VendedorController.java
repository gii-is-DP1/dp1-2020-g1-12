package org.springframework.samples.dpc.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.SolicitudService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

	private VendedorService vendedorService;
	private ArticuloService articuloService;
	private ClienteService clienteService;
	private SolicitudService solicitudService;

	@Autowired
	public VendedorController(VendedorService vendedorService, ArticuloService articuloService,
			ClienteService clienteService, SolicitudService solicitudService) {
		this.vendedorService = vendedorService;
		this.articuloService = articuloService;
		this.clienteService = clienteService;
		this.solicitudService = solicitudService;
	}

	@GetMapping(value = "/perfil")
	public String mostrarPerfil(ModelMap modelMap) {
		String perfil = "vendedores/perfil";
		Vendedor optperfil = vendedorService.findSellerById(vendedorService.obtenerIdSesion()); 
																										
		modelMap.addAttribute("vendedor", optperfil);
		return perfil;
	}

	@GetMapping(value = "/editar")
	public String editar(Model model) {
		Vendedor vendedor = this.vendedorService.findSellerById(vendedorService.obtenerIdSesion());
		model.addAttribute(vendedor);
		return "vendedores/editarPerfil";
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Vendedor vendedor, BindingResult result) {
		if (result.hasErrors()) {
			return "vendedores/editarPerfil";
		} else {
			this.vendedorService.editar(vendedor, vendedorService.obtenerIdSesion());
			return "redirect:/vendedores/perfil";
		}
	}

	@GetMapping(value = "/perfilCliente/{clienteId}")
	public String mostrarPerfilCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
		Cliente cliente = this.clienteService.findClientById(clienteId);
		modelMap.addAttribute("cliente", cliente);
		modelMap.remove(cliente.getDni());
		return "vendedores/perfilCliente";
	}

	@GetMapping(value = "/articulosEnVenta")
	public String mostrarArticulosEnVenta(ModelMap modelMap) {
		String vista = "vendedores/listadoArticulos";
		Iterable<Articulo> optarticulos = articuloService.articulosEnVentaByProvider(vendedorService.obtenerIdSesion());
		modelMap.addAttribute("articulos", optarticulos);
		return vista;
	}

	@GetMapping(value = "/listadoSolicitudes")
	public String mostrarListadoSolicitudes(ModelMap modelMap) {
		String vista = "vendedores/listadoSolicitudes";
		List<Solicitud> solicitudes = solicitudService.getsolicitudesByProvider(vendedorService.obtenerIdSesion());
		modelMap.addAttribute("solicitudes", solicitudes);
		return vista;
	}

	@GetMapping(value = "/articulo/{articuloId}")
	public String mostrarArticuloDetallado(@PathVariable("articuloId") int articuloId, ModelMap modelMap) {
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
		Solicitud solicitud = solicitudService.detallesSolicitud(solicitudId);

		if (solicitud != null && solicitud.getSituacion().equals(Situacion.Pendiente)
				&& solicitud.getVendedor().equals(vendedorService.getVendedorDeSesion())) {
			solicitudService.eliminarSolicitud(solicitud);
		}
		return "redirect:/vendedores/listadoSolicitudes";
	}

	@GetMapping(value = "/articulosVendidos")
	public String mostrarArticulosVendidos(ModelMap modelMap) {
		String vista = "vendedores/listadoArticulos";
		Iterable<Articulo> optarticulos = articuloService
				.articulosVendidosByProvider(vendedorService.obtenerIdSesion());
		modelMap.addAttribute("articulos", optarticulos);
		return vista;
	}

}
