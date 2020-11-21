package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticuloService {

	@Autowired
	private ArticuloRepository articuloRepository;
	
	public List<Articulo> getArticulosByProvider() {
		
		return null;
	}
}
