package org.springframework.samples.dpc.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Moderador;
import org.springframework.samples.dpc.repository.ModeradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeradorService {
	
	private final ModeradorRepository moderadorRepository;
	private final UserService userService;
	
	public ModeradorService(ModeradorRepository moderadorRepository, UserService userService) {
		this.moderadorRepository = moderadorRepository;
		this.userService = userService;
	}
	
	@Transactional
	public Integer obtenerIdSesion() {
		return moderadorRepository.moderadorId(userService.obtenerUsername());
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
		return (moderadorRepository.findById(id).isPresent()) ? moderadorRepository.findById(id).get() : null;
	}
	
	@Transactional(readOnly = true)
	public Moderador getModeradorDeSesion() throws DataAccessException {
		return findModeradorById(obtenerIdSesion());
	}
}
