package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Situacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SolicitudService {
	
	@Autowired
	private SolicitudRepository solicitudRepository;

	@Transactional
	public Iterable<Solicitud> solicitudesPendientes() {
		Iterable<Solicitud> result = solicitudRepository.findAll();
		List<Solicitud> lista = new ArrayList<>();
		for(Solicitud sol:result) {
			if(sol.getSituacion().equals(Situacion.Pendiente)) {
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
		Optional<Solicitud> solicitud = solicitudRepository.findById(solicitudId);
		solicitud.get().setSituacion(Situacion.Aceptada);
	}
	@Transactional
	public void denegarSolicitud(Integer solicitudId,String respuesta) {
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
	
	
	
	
}
