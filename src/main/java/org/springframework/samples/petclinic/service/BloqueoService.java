package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Bloqueo;
import org.springframework.samples.petclinic.repository.BloqueoRepository;
import org.springframework.samples.petclinic.service.exceptions.BloquearSinDescripcionException;
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

	@Transactional(rollbackFor = BloquearSinDescripcionException.class)
	public void editar(@Valid Bloqueo bloqueo, Integer id, Boolean bloqueado) throws BloquearSinDescripcionException {
		Bloqueo bloqueoGuardado = findBlockById(id);
		if(bloqueado) {
			if(bloqueo.getDescripcion().length() > 20 && bloqueo.getDescripcion().length() < 250) {
				bloqueoGuardado.setDescripcion(bloqueo.getDescripcion());
			}
			else {
				throw new BloquearSinDescripcionException();
			}
		}
		else {
			bloqueoGuardado.setDescripcion("");
		}
		bloqueoGuardado.setBloqueado(bloqueado);
	}
}
