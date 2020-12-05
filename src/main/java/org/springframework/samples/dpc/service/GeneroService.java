package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.repository.GeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneroService {
	
	@Autowired
	private GeneroRepository generoRepository;

	
	@Transactional(readOnly = true)
	public Genero findGeneroById(int id) throws DataAccessException {
		return generoRepository.findById(id).get();
	}
}
