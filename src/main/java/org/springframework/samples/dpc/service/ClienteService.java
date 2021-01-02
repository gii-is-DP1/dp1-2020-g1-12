package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final UserService userService;

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
		return (clienteRepository.findById(id).isPresent()) ? clienteRepository.findById(id).get() : null;
	}

	@Transactional(readOnly = true)
	public Page<Cliente> findAllClient(Integer page, Integer size, String orden) {
		Pageable pageable = obtenerFiltros(page, size, orden);
		return clienteRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Cliente findClientByDni(String dni) throws DataAccessException {
		return clienteRepository.findByDni(dni);
	}

	@Transactional(readOnly = true)
	public Cliente getClienteDeSesion() throws DataAccessException {
		return findClientById(obtenerIdSesion());
	}
	
	@Transactional(readOnly = true)
	public Bloqueo getBloqueoCliente(String username) throws DataAccessException {
		return clienteRepository.clienteBloqueo(username);
	}
	
	@Transactional(readOnly = true)
	public Pageable obtenerFiltros(Integer page, Integer size, String orden) throws ResponseStatusException{
		if(!orden.equals("nombre") && !orden.equals("-nombre") && !orden.equals("apellido") && !orden.equals("-apellido") &&
				!orden.equals("dni") && !orden.equals("-dni")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parámetro de búsqueda introducido no "
					+ "válido.");
		}
		Order order = orden.startsWith("-") ? new Order(Sort.Direction.DESC, orden.replace("-", "")) :
			new Order(Sort.Direction.ASC, orden);
	
		return PageRequest.of(page, size, Sort.by(order));
	}
}
