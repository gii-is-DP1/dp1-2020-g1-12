package org.springframework.samples.dpc.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.repository.BloqueoRepository;
import org.springframework.samples.dpc.service.exceptions.BloquearSinDescripcionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BloqueoService {

	@Autowired
	private BloqueoRepository bloqueoRepository;
	
	@Transactional(readOnly = true)
	public Bloqueo findBlockById(int id) throws DataAccessException {
		return (bloqueoRepository.findById(id).isPresent()) ? bloqueoRepository.findById(id).get() : null;
	}
	
	@Transactional
	public void guardar(Bloqueo bloqueo) {
		bloqueoRepository.save(bloqueo);
	}

	@Transactional(rollbackFor = BloquearSinDescripcionException.class)
	public void editar(@Valid Bloqueo bloqueo, Integer id, Boolean bloqueado) throws BloquearSinDescripcionException {
		Bloqueo bloqueoGuardado = findBlockById(id);
		if(bloqueado) {
			if(bloqueo.getDescripcion().length() > 10 && bloqueo.getDescripcion().length() < 200) {
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
