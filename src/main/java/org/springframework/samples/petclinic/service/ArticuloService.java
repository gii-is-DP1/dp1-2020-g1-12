package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.ArticuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticuloService {

	private final ArticuloRepository articuloRepository;

	@Autowired
	public ArticuloService(ArticuloRepository articuloRepository) {
		this.articuloRepository = articuloRepository;
	}

	@Transactional
	public void guardarArticulo(Articulo articulo) {
		articuloRepository.save(articulo);
	}

	@Transactional
	public List<Articulo> articulosEnVentaByProvider(Integer id) {
		return articuloRepository.articulosEnVentaPorId(id);
	}

	@Transactional(readOnly = true)
	public Articulo findArticuloById(int id) throws DataAccessException {
		return articuloRepository.findById(id).get();
	}
}
