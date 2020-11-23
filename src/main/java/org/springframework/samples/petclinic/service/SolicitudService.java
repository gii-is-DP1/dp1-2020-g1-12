package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Situacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.ArticuloRepository;
import org.springframework.samples.petclinic.repository.OfertaRepository;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

	@Autowired
	private SolicitudRepository solicitudRepository;

	@Autowired
	private ArticuloRepository articuloRepository;
	
	@Autowired
	private OfertaRepository ofertaRepository;

	@Transactional
	public Iterable<Solicitud> solicitudesPendientes() {
		Iterable<Solicitud> result = solicitudRepository.findAll();
		List<Solicitud> lista = new ArrayList<>();
		for (Solicitud sol : result) {
			if (sol.getSituacion().equals(Situacion.Pendiente)) {
				lista.add(sol);
			}
		}
		return lista;
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
		articuloRepository.save(articulo);
		ofertaRepository.save(oferta);
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
	public List<Articulo> articulosEnVentaByProvider(Collection<Solicitud> solicitudes) {
		List<Articulo> result = new ArrayList<>();

		for (Solicitud sol : solicitudes) {
			if (sol.getSituacion().equals(Situacion.Aceptada) && sol.getStock() > 0) {
				result.add(sol.getArticulo());
			}
		}
		return result;
	}

	@Transactional
	public List<Articulo> articulosVendidosByProvider(Collection<Solicitud> solicitudes) {
		List<Articulo> result = new ArrayList<>();

		for (Solicitud sol : solicitudes) {
			if (sol.getSituacion().equals(Situacion.Aceptada)) {
				result.add(sol.getArticulo());
			}
		}
		return result;
	}
}
