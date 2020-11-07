package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Moderador;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.repository.ModeradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeradorService {
	
	@Autowired
	private ModeradorRepository moderadorRepository;
	//private ClienteRepository clienteRepository;
	

	@Transactional
	public int moderadorCount() {
		return (int) moderadorRepository.count();
	}
	
	@Transactional
	public Optional<Moderador> datosPerfil(Integer moderadorId){
		Optional<Moderador> result = moderadorRepository.findById(moderadorId);
		return result;
	}
	
	public void guardar(Moderador moderador) {
		moderadorRepository.save(moderador);		
	}
	
	@Transactional(readOnly = true)
	public Moderador findModeradorById(int id) throws DataAccessException {
		return moderadorRepository.findById(id).get();
	}
	

	

	
}
