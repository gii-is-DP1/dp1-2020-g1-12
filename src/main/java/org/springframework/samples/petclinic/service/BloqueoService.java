package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.repository.BloqueoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BloqueoService {

	@Autowired
	private BloqueoRepository bloqueoRepository;
	
	
	@Transactional(readOnly = true)
	public Bloqueo findBlockById(int id) throws DataAccessException {
		return bloqueoRepository.findById(id).get();
	}

	@Transactional
	public void editar(@Valid Bloqueo bloqueo, Integer id, Boolean bloqueado) {
		Bloqueo bloqueoGuardado = findBlockById(id);
		if(bloqueado) {
			bloqueoGuardado.setDescripcion(bloqueo.getDescripcion());
		}
		else {
			bloqueoGuardado.setDescripcion("");
		}
		bloqueoGuardado.setBloqueado(bloqueado);
	}
}
