package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final UserService userService;

	@Transactional
	public int clienteCount() {
		return (int) clienteRepository.count();
	}

	@Autowired
	public ClienteService(ClienteRepository clienteRepository, UserService userService) {
		this.clienteRepository = clienteRepository;
		this.userService = userService;
	}

	@Transactional
	public Integer obtenerIdSesion() {
		return clienteRepository.clienteId(userService.obtenerUsername());
	}

	@Transactional
	public Optional<Cliente> datosPerfil(Integer clienteId) {
		Optional<Cliente> result = clienteRepository.findById(clienteId);
		return result;
	}

	@Transactional
	public void guardar(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Transactional
	public void editar(Cliente cliente, Integer id) {
		Cliente clienteGuardado = findClientById(id);
		clienteGuardado.setApellido(cliente.getApellido());
		clienteGuardado.setDireccion(cliente.getDireccion());
		clienteGuardado.setDni(cliente.getDni());
		clienteGuardado.setEmail(cliente.getEmail());
		clienteGuardado.setNombre(cliente.getNombre());
		clienteGuardado.setTelefono(cliente.getTelefono());
	}

	@Transactional(readOnly = true)
	public Cliente findClientById(int id) throws DataAccessException {
		return clienteRepository.findById(id).get();
	}

	public Iterable<Cliente> findAllClient() {
		Iterable<Cliente> result = clienteRepository.findAll();
		return result;
	}

	@Transactional(readOnly = true)
	public Cliente findClientByDni(String dni) throws DataAccessException {
		return clienteRepository.findByDni(dni);
	}

}
