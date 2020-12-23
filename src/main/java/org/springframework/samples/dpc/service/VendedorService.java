package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.VendedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendedorService {

	private final VendedorRepository vendedorRepository;
	private final UserService userService;

	@Autowired
	public VendedorService(VendedorRepository vendedorRepository, UserService userService) {
		this.vendedorRepository = vendedorRepository;
		this.userService = userService;
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

	@Transactional
	public void editar(Vendedor vendedor, Integer id) {
		Vendedor vendedorGuardado = findSellerById(id);
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

	public Iterable<Vendedor> findAllSeller() {
		return vendedorRepository.findAll();
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
