package org.springframework.samples.dpc.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.repository.OfertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfertaService {

	private OfertaRepository ofertaRepository;
	
	@Autowired
	public OfertaService(OfertaRepository ofertaRepository) {
		this.ofertaRepository = ofertaRepository;
	}

	@Transactional(readOnly = true)
	public Oferta findOfertById(int id) throws DataAccessException {
		return (ofertaRepository.findById(id).isPresent()) ? ofertaRepository.findById(id).get() : null;
	}

	@Transactional
	public void editar(@Valid Oferta oferta, Integer id, Boolean disponible) {
		Oferta ofertaGuardada = findOfertById(id);
		if(oferta.getPorcentaje() != null) {
			ofertaGuardada.setPorcentaje(oferta.getPorcentaje());
		}
		else {
			ofertaGuardada.setPorcentaje(5);
		}
		ofertaGuardada.setDisponibilidad(disponible);
	}
	
	@Transactional
	public void guardarOferta(Oferta oferta) {
		ofertaRepository.save(oferta);
	}
}
