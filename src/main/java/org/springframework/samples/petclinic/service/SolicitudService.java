package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Situacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SolicitudService {
	
	
	private SolicitudRepository solicitudRepository;	
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository) {
		super();
		this.solicitudRepository = solicitudRepository;
	}

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
	public void guardar(Solicitud solicitud) {
		solicitudRepository.save(solicitud);
	}
	
	
	
	
}
