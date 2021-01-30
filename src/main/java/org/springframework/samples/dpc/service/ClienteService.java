package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.repository.ClienteRepository;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	private UserService userService;
	private ArticuloService articuloService;
	private BloqueoService bloqueoService;
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository, UserService userService, 
			ArticuloService articuloService,@Lazy BloqueoService bloqueoService, AuthoritiesService authoritiesService) {
		this.clienteRepository = clienteRepository;
		this.userService = userService;
		this.articuloService = articuloService;
		this.bloqueoService = bloqueoService;
		this.authoritiesService = authoritiesService;
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
	public void registroCliente(Cliente cliente) {
		String cifrado = new BCryptPasswordEncoder().encode(cliente.getUser().getPassword());
		cliente.getUser().setPassword(cifrado);
		Cesta cesta = new Cesta();
		Bloqueo b = new Bloqueo();
		b.setBloqueado(false);
		bloqueoService.guardar(b);
		cliente.setBloqueo(b);
		cliente.getUser().setEnabled(true);
		cliente.setCesta(cesta);
		guardar(cliente);
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}

	@Transactional(rollbackFor = {ContrasenyaNoCoincideException.class, ContrasenyaNecesariaException.class})
	public void editar(Cliente cliente, Integer id) throws Exception {
		Cliente clienteGuardado = findClientById(id);
		User clienteUser = cliente.getUser();
		if(clienteUser.getPassword()!="" && clienteUser.getUsername()!="") {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(clienteUser.getPassword(), clienteGuardado.getUser().getPassword())) {
				String cifrado = new BCryptPasswordEncoder().encode(clienteUser.getUsername()); //esta sería la nueva contraseña, habría que mirar si  cumple con el patrón
				clienteGuardado.getUser().setPassword(cifrado);
			}else {
				throw new ContrasenyaNoCoincideException();
			}
		}else if(clienteUser.getPassword()=="" && clienteUser.getUsername()!="") {
			throw new ContrasenyaNecesariaException();
		}
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
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "clientes");
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
	public Boolean getValidaChat(Integer articuloId, Integer clienteId) throws DataAccessException {
		return clienteRepository.haCompradoArticulo(articuloId, clienteId) > 0 ? true : false;
	}
}
