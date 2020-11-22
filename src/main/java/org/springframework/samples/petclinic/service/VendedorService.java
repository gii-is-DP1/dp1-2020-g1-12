package org.springframework.samples.petclinic.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.VendedorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository vendedorRepository;

	@Transactional
	public int vendedorCount() {
		return (int) vendedorRepository.count();
	}

	@Transactional
	public Optional<Vendedor> datosPerfil(Integer vendedorId) {
		Optional<Vendedor> result = vendedorRepository.findById(vendedorId);
		return result;
	}

	public void guardar(Vendedor vendedor) {
		vendedorRepository.save(vendedor);
	}

	@Transactional(readOnly = true)
	public Vendedor findSellerById(int id) throws DataAccessException {
		return vendedorRepository.findById(id).get();
	}

	public Iterable<Vendedor> findAllSeller() {
		Iterable<Vendedor> result = vendedorRepository.findAll();
		return result;
	}

}
