package org.springframework.samples.dpc.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Genero;
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
	
	@Transactional(readOnly = true)
	public List<Articulo> articulosRelacionados(Set<Genero> generos, int articuloId) {
		List<Articulo> relacionados = new ArrayList<>();
		List<Articulo> articulos = articuloRepository.articulosDisponibles();
		articulos.sort(Comparator.comparing(Articulo::getId).reversed());
		for(Articulo articulo:articulos) {
			if(relacionados.size() < 6 && articulo.getId() != articuloId && articulo.getGeneros().containsAll(generos))
				relacionados.add(articulo);
		}
		return relacionados;
	}
	
}
