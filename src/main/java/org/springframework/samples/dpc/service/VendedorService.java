package org.springframework.samples.dpc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public int vendedorCount() {
		return (int) vendedorRepository.count();
	}

	@Transactional
	public Integer obtenerIdSesion() {
		return vendedorRepository.vendedorId(userService.obtenerUsername());
	}
	
	@Transactional
	public Vendedor vendedorDeUnArticulo(Integer articuloId) {
		Vendedor result = vendedorRepository.vendedorDeArticulo(articuloId);
		return result;
	}

	@Transactional
	public Optional<Vendedor> datosPerfil(Integer vendedorId) {
		Optional<Vendedor> result = vendedorRepository.findById(vendedorId);
		return result;
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
		return vendedorRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Vendedor findSellerByDni(String dni) throws DataAccessException {
		return vendedorRepository.findByDni(dni);
	}

	public Iterable<Vendedor> findAllSeller() {
		Iterable<Vendedor> result = vendedorRepository.findAll();
		return result;
	}
}
