package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.repository.OfertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfertaService {

	private final OfertaRepository ofertaRepository;
	
	@Autowired
	public OfertaService(OfertaRepository ofertaRepository) {
		this.ofertaRepository = ofertaRepository;
	}

	@Transactional(readOnly = true)
	public Oferta findOfertById(int id) throws DataAccessException {
		return ofertaRepository.findById(id).get();
	}

	@Transactional
	public void editar(@Valid Oferta oferta, Integer id, Boolean disponible) {
		Oferta ofertaGuardada = findOfertById(id);
		ofertaGuardada.setPorcentaje(oferta.getPorcentaje());
		ofertaGuardada.setDisponibilidad(disponible);
	}
	
	@Transactional
	public void guardarOferta(Oferta oferta) {
		ofertaRepository.save(oferta);
	}
}
