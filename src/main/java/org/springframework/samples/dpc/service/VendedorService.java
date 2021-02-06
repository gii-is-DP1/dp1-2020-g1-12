package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.VendedorRepository;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNecesariaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoValidaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaParecidaUsuarioException;
import org.springframework.samples.dpc.service.exceptions.UsernameDuplicadoException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendedorService {

	private VendedorRepository vendedorRepository;
	private UserService userService;
	private ArticuloService articuloService;
	private BloqueoService bloqueoService;
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public VendedorService(VendedorRepository vendedorRepository, UserService userService, 
			ArticuloService articuloService,@Lazy BloqueoService bloqueoService, AuthoritiesService authoritiesService) {
		this.vendedorRepository = vendedorRepository;
		this.userService = userService;
		this.articuloService = articuloService;
		this.bloqueoService = bloqueoService;
		this.authoritiesService = authoritiesService;
	}

	@Transactional
	public Integer obtenerIdSesion() {
		return vendedorRepository.vendedorId(userService.obtenerUsername());
	}
	
	@Transactional
	public Vendedor vendedorDeUnArticulo(Integer articuloId) {
		return vendedorRepository.vendedorDeArticulo(articuloId);
	}
	
	@Transactional
	public void eliminarSolicitud(Solicitud solicitud, Vendedor vendedor) {
		vendedor.getSolicitudes().remove(solicitud);
	}
	
	@Transactional
	public Boolean esVendedorDelArticulo(Integer articuloId) {
		return vendedorRepository.vendedorDeArticulo(articuloId).equals(getVendedorDeSesion());
	}

	@Transactional
	public void guardar(Vendedor vendedor) {
		vendedorRepository.save(vendedor);
	}
	@Transactional(rollbackFor= {UsernameDuplicadoException.class, ContrasenyaNoValidaException.class, ContrasenyaNoCoincideException.class, ContrasenyaParecidaUsuarioException.class})
	public void registroVendedor(Vendedor vendedor) throws Exception {
		User usuario = vendedor.getUser();
		if(userService.findUser(usuario.getUsername()) != null || usuario.getUsername().length() < 4) {
			throw new UsernameDuplicadoException();
		}
		if(!usuario.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$")) {
			throw new ContrasenyaNoValidaException();
		}
		if(!usuario.getPassword().equals(usuario.getNewPassword())) {
			throw new ContrasenyaNoCoincideException();
		}
		if(usuario.getPassword().equals(usuario.getUsername())) {
			throw new ContrasenyaParecidaUsuarioException();
		}
		String cifrado = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setPassword(cifrado);
		Bloqueo b = new Bloqueo();
		b.setBloqueado(false);
		bloqueoService.guardar(b);
		vendedor.setBloqueo(b);
		usuario.setEnabled(true);
		guardar(vendedor);
		authoritiesService.saveAuthorities(usuario.getUsername(), "vendedor");
	}

	@Transactional(rollbackFor = {ContrasenyaNoCoincideException.class, ContrasenyaNecesariaException.class, ContrasenyaNoValidaException.class, ContrasenyaParecidaUsuarioException.class})
	public void editar(Vendedor vendedor, Integer id) throws Exception {
		Vendedor vendedorGuardado = findSellerById(id);
		User vendedorUser = vendedor.getUser();
		
		if(!vendedorUser.getPassword().equals("") && !vendedorUser.getNewPassword().equals("")) {
			if(!vendedorUser.getNewPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$")) {
				throw new ContrasenyaNoValidaException();
			}
			if(vendedorUser.getNewPassword().equals(vendedorGuardado.getUser().getUsername())) {
				throw new ContrasenyaParecidaUsuarioException();
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(vendedorUser.getPassword(), vendedorGuardado.getUser().getPassword())) {
				String cifrado = new BCryptPasswordEncoder().encode(vendedorUser.getNewPassword()); //esta sería la nueva contraseña, habría que mirar si  cumple con el patrón
				vendedorGuardado.getUser().setPassword(cifrado);
			}else {
				throw new ContrasenyaNoCoincideException();
			}
		}else if(vendedorUser.getPassword().equals("") && !vendedorUser.getNewPassword().equals("")) {
			throw new ContrasenyaNecesariaException();
		}
		vendedorGuardado.setApellido(vendedor.getApellido());
		vendedorGuardado.setDireccion(vendedor.getDireccion());
		vendedorGuardado.setDni(vendedor.getDni());
		vendedorGuardado.setEmail(vendedor.getEmail());
		vendedorGuardado.setNombre(vendedor.getNombre());
		vendedorGuardado.setTelefono(vendedor.getTelefono());
	}

	@Transactional(readOnly = true)
	public Vendedor findSellerById(int id) throws DataAccessException {
		return (vendedorRepository.findById(id).isPresent()) ? vendedorRepository.findById(id).get() : null;
	}

	@Transactional(readOnly = true)
	public Vendedor findSellerByDni(String dni) throws DataAccessException {
		return vendedorRepository.findByDni(dni);
	}

	public Page<Vendedor> findAllSeller(Integer page, Integer size, String orden) {
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "clientes");
		return vendedorRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Vendedor getVendedorDeSesion() throws DataAccessException {
		return findSellerById(obtenerIdSesion());
	}
	
	@Transactional(readOnly = true)
	public Bloqueo getBloqueoVendedor(String username) throws DataAccessException {
		return vendedorRepository.vendedorBloqueo(username);
	}
}
