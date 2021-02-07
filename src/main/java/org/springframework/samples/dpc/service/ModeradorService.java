package org.springframework.samples.dpc.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Moderador;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.repository.ModeradorRepository;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoValidaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaParecidaUsuarioException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeradorService {
	
	private ModeradorRepository moderadorRepository;
	private UserService userService;
	
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
	
	@Transactional(rollbackFor = {ContrasenyaNoCoincideException.class, ContrasenyaNecesariaException.class, ContrasenyaNoValidaException.class, ContrasenyaParecidaUsuarioException.class})
	public void editar(Moderador moderador, Integer id) throws Exception {
		Moderador moderadorGuardado = findModeradorById(id);
		User moderadorUser = moderador.getUser();
		if(!moderadorUser.getPassword().equals("") && !moderadorUser.getNewPassword().equals("")) {
			if(!moderadorUser.getNewPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$")) {
				throw new ContrasenyaNoValidaException();
			}
			if(moderadorUser.getNewPassword().equals(moderadorGuardado.getUser().getNewPassword())) {
				throw new ContrasenyaParecidaUsuarioException();
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(moderadorUser.getPassword(), moderadorGuardado.getUser().getPassword())) {
				String cifrado = new BCryptPasswordEncoder().encode(moderadorUser.getNewPassword());
				moderadorGuardado.getUser().setPassword(cifrado);
			} else {
				throw new ContrasenyaNoCoincideException();
			}
		} else if(moderadorUser.getPassword().equals("") && !moderadorUser.getNewPassword().equals("")) {
			throw new ContrasenyaNecesariaException();
		}
		moderadorGuardado.setNombre(moderador.getNombre());
		moderadorGuardado.setApellido(moderador.getApellido());
		moderadorGuardado.setDni(moderador.getDni());
		moderadorGuardado.setDireccion(moderador.getDireccion());
		moderadorGuardado.setTelefono(moderador.getTelefono());
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
