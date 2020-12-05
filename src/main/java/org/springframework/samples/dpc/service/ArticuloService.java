package org.springframework.samples.dpc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.repository.ArticuloRepository;
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
	public void eliminarArticulo(Integer articuloId) {
		Optional<Articulo> articulo = articuloRepository.findById(articuloId);
		articulo.get().setStock(0);
	}

	@Transactional
	public List<Articulo> articulosEnVentaByProvider(Integer id) {
		return articuloRepository.articulosEnVentaPorId(id);
	}

	@Transactional(readOnly = true)
	public Articulo findArticuloById(int id) throws DataAccessException {
		return articuloRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public List<Articulo> articulosDisponibles() {
		return articuloRepository.articulosDisponibles();
	}
	
}
