package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Moderador;
import org.springframework.samples.petclinic.repository.ModeradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeradorService {
	
	@Autowired
	private ModeradorRepository moderadorRepository;
	
	@Transactional
	public Optional<Moderador> datosPerfil(Integer moderadorId){
		Optional<Moderador> result = moderadorRepository.findById(moderadorId);
		return result;
	}
	
	@Transactional
	public void guardar(Moderador moderador) {
		moderadorRepository.save(moderador);		
	}
	
	@Transactional
	public void editar(Moderador moderador, Integer moderadorId) {
		Moderador mod = moderadorRepository.findById(moderadorId).get();
		mod.setNombre(moderador.getNombre());
		mod.setApellido(moderador.getApellido());
		mod.setDni(moderador.getDni());
		mod.setDireccion(moderador.getDireccion());
		mod.setTelefono(moderador.getTelefono());
	}
	
	@Transactional(readOnly = true)
	public Moderador findModeradorById(int id) throws DataAccessException {
		return moderadorRepository.findById(id).get();
	}
	

	

	
}
