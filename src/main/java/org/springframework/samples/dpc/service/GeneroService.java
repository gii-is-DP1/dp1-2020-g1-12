package org.springframework.samples.dpc.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.repository.GeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneroService {
	
	private GeneroRepository generoRepository;
	private ArticuloService articuloService;
	
	@Autowired
	public GeneroService(GeneroRepository generoRepository, ArticuloService articuloService) {
		this.generoRepository = generoRepository;
		this.articuloService = articuloService;
	}
	
	@Transactional(readOnly = true)
	public Genero findGeneroById(int id) throws DataAccessException {
		return (generoRepository.findById(id).isPresent()) ? generoRepository.findById(id).get() : null;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Genero> findAllGeneros() throws DataAccessException {
		return generoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Genero> generosRestantes(int articuloId) throws DataAccessException {
		Set<Genero> generosActuales = articuloService.findArticuloById(articuloId).getGeneros();
		return generoRepository.generosRestantes(generosActuales);
	}
	
	@Transactional
	public void anyadirGenero(int articuloId, Genero genero) {
		if(genero.getId() != null)
			articuloService.findArticuloById(articuloId).getGeneros().add(genero);
	}
	
	@Transactional
	public void eliminarGenero(int articuloId, int generoId) {
		articuloService.findArticuloById(articuloId).getGeneros().remove(generoRepository.findById(generoId).get());
	}
}
