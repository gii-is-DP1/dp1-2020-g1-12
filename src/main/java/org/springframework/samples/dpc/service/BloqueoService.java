package org.springframework.samples.dpc.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.repository.BloqueoRepository;
import org.springframework.samples.dpc.service.exceptions.BloquearSinDescripcionException;
import org.springframework.samples.dpc.service.exceptions.UsuarioBloqueadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BloqueoService {

	private BloqueoRepository bloqueoRepository;
	private ClienteService clienteService;
	private VendedorService vendedorService;
	private UserService userService;

	@Autowired
	public BloqueoService(BloqueoRepository bloqueoRepository, ClienteService clienteService,
			VendedorService vendedorService, UserService userService) {
		this.bloqueoRepository = bloqueoRepository;
		this.clienteService = clienteService;
		this.vendedorService = vendedorService;
		this.userService = userService;
	}

	@Transactional(readOnly = true)
	public Bloqueo findBlockById(int id) throws DataAccessException {
		return (bloqueoRepository.findById(id).isPresent()) ? bloqueoRepository.findById(id).get() : null;
	}

	@Transactional(rollbackFor = UsuarioBloqueadoException.class)
	public void usuarioBloqueado(String username) throws UsuarioBloqueadoException {
		String autoridad = userService.getAuthority(username);

		if ((autoridad.equals("vendedor") && vendedorService.getBloqueoVendedor(username).isBloqueado()) ||
				(autoridad.equals("cliente") && clienteService.getBloqueoCliente(username).isBloqueado())) {
			throw new UsuarioBloqueadoException();
		}
	}
	
	@Transactional
	public String usuarioBloqueadoMotivo(String username) {
		String autoridad = userService.getAuthority(username);
		String motivo = "";

		if (autoridad.equals("vendedor")) {
			motivo = vendedorService.getBloqueoVendedor(username).getDescripcion();
		} 
		else if (autoridad.equals("cliente")) {
			motivo = clienteService.getBloqueoCliente(username).getDescripcion();
		}
		return motivo;
	}

	@Transactional
	public void guardar(Bloqueo bloqueo) {
		bloqueoRepository.save(bloqueo);
	}

	@Transactional(rollbackFor = BloquearSinDescripcionException.class)
	public void editar(@Valid Bloqueo bloqueo, Integer id, boolean bloqueado) throws BloquearSinDescripcionException {
		Bloqueo bloqueoGuardado = findBlockById(id);

		if (bloqueado) {
			if (bloqueo.getDescripcion().length() > 10 && bloqueo.getDescripcion().length() < 200) {
				bloqueoGuardado.setDescripcion(bloqueo.getDescripcion());
			} else {
				throw new BloquearSinDescripcionException();
			}
		} else {
			bloqueoGuardado.setDescripcion("");
		}
		bloqueoGuardado.setBloqueado(bloqueado);
	}
}
