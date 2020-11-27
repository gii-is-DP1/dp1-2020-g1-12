package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Situacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

	private final SolicitudRepository solicitudRepository;
	private final ArticuloService articuloService;
	private final OfertaService ofertaService;
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository, OfertaService ofertaService, 
			ArticuloService articuloService) {
		this.solicitudRepository = solicitudRepository;
		this.articuloService = articuloService;
		this.ofertaService = ofertaService;
	}
	
	@Transactional
	public List<Solicitud> solicitudesPendientes() {
		return solicitudRepository.solicitudesPendientes();
	}

	@Transactional
	public Optional<Solicitud> detallesSolicitud(Integer solicitudId) {
		Optional<Solicitud> result = solicitudRepository.findById(solicitudId);
		return result;
	}

	@Transactional
	public void aceptarSolicitud(Integer solicitudId) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).get();
		solicitud.setSituacion(Situacion.Aceptada);
		Oferta oferta = new Oferta();
		oferta.setDisponibilidad(false);
		oferta.setPorcentaje(5);
		Articulo articulo = new Articulo();
		articulo.setGastoEnvio(solicitud.getGastoEnvio());
		articulo.setMarca(solicitud.getMarca());
		articulo.setModelo(solicitud.getModelo());
		articulo.setPrecio(solicitud.getPrecio());
		articulo.setStock(solicitud.getStock());
		articulo.setTiempoEntrega(solicitud.getTiempoEntrega());
		articulo.setTipo(solicitud.getTipo());
		articulo.setUrlImagen(solicitud.getUrlImagen());
		articulo.setOferta(oferta);
		solicitud.setArticulo(articulo);
		articuloService.guardarArticulo(articulo);
		ofertaService.guardarOferta(oferta);
	}

	@Transactional
	public void denegarSolicitud(Integer solicitudId, String respuesta) {
		Optional<Solicitud> solicitud = solicitudRepository.findById(solicitudId);
		solicitud.get().setRespuesta(respuesta);
		solicitud.get().setSituacion(Situacion.Denegada);
	}

	@Transactional
	public void guardar(Solicitud solicitud, Vendedor vendedor) {
		solicitud.setVendedor(vendedor);
		solicitud.setSituacion(Situacion.Pendiente); // Por defecto, la solicitud tiene situación "Pendiente".
		solicitud.setRespuesta(""); // Por defecto, la solicitud no tiene una respuesta.
		solicitudRepository.save(solicitud);
	}
	
	@Transactional
	public Solicitud findById(Integer id) {
		return solicitudRepository.findById(id).get();
	}
	
}
