package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.VendedorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository vendedorRepository;
	
	public Iterable<Vendedor> findAllSeller() {
		Iterable<Vendedor> result = vendedorRepository.findAll();
		return result;	}

}
