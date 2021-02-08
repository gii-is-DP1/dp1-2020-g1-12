package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.SolicitudRepository;
import org.springframework.samples.dpc.service.exceptions.PrecioMenorAlEnvioException;
import org.springframework.samples.dpc.service.exceptions.SolicitudRechazadaSinRespuestaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

	private SolicitudRepository solicitudRepository;
	private ArticuloService articuloService;
	private OfertaService ofertaService;
	private VendedorService vendedorService;
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository, OfertaService ofertaService, 
			ArticuloService articuloService, VendedorService vendedorService) {
		this.solicitudRepository = solicitudRepository;
		this.articuloService = articuloService;
		this.ofertaService = ofertaService;
		this.vendedorService = vendedorService;
	}
	
	
	@Transactional
	public Page<Solicitud> solicitudesPendientes(Integer page, Integer size, String orden) {
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "articulo");
		return solicitudRepository.solicitudesPendientes(pageable);
	}
	
	@Transactional
	public Page<Solicitud> getsolicitudesByProvider(Integer vendedorId, Integer page, Integer size, String orden) {
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "articulo");
		return solicitudRepository.findByVendedor(vendedorId, pageable);
	}

	@Transactional
	public Solicitud detallesSolicitud(Integer id) {
		return (solicitudRepository.findById(id).isPresent()) ? solicitudRepository.findById(id).get() : null;
	}
	
	@Transactional
	public void eliminarSolicitud(Solicitud solicitud) {
		vendedorService.eliminarSolicitud(solicitud, solicitud.getVendedor());
		solicitudRepository.delete(solicitud);
	}


	@Transactional
	public void aceptarSolicitud(Integer solicitudId) {
		Solicitud solicitud = solicitudRepository.findById(solicitudId).get();
		solicitud.setSituacion(Situacion.Aceptada);
		Oferta oferta = new Oferta();
		oferta.setDisponibilidad(false);
		oferta.setPorcentaje(5);
		Articulo articulo = new Articulo();
		articulo.setDescripcion(solicitud.getDescripcion());
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

	@Transactional(rollbackFor = SolicitudRechazadaSinRespuestaException.class)
	public void denegarSolicitud(Integer solicitudId, String respuesta) throws SolicitudRechazadaSinRespuestaException {
		if(respuesta.isEmpty() || (respuesta.length() < 15) || respuesta.length() > 100) {
			throw new SolicitudRechazadaSinRespuestaException();
		} else {
			Solicitud solicitud = detallesSolicitud(solicitudId);
			solicitud.setRespuesta(respuesta);
			solicitud.setSituacion(Situacion.Denegada);
		}
	}

	@Transactional(rollbackFor = PrecioMenorAlEnvioException.class)
	public void guardar(Solicitud solicitud, Vendedor vendedor) throws PrecioMenorAlEnvioException {
		if(solicitud.getPrecio() <= solicitud.getGastoEnvio()) {
			throw new PrecioMenorAlEnvioException();
		} else {	
			solicitud.setVendedor(vendedor);
			solicitud.setSituacion(Situacion.Pendiente); // Por defecto, la solicitud tiene situación "Pendiente".
			solicitud.setRespuesta(""); // Por defecto, la solicitud no tiene una respuesta.
			solicitudRepository.save(solicitud);
		}
	}
	
}
