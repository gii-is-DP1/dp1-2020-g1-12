package org.springframework.samples.dpc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {
	
	private final ComentarioRepository comentarioRepository;
	private final ArticuloService articuloService;

	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository, ArticuloService articuloService) {
		this.comentarioRepository = comentarioRepository;
		this.articuloService = articuloService;
	}
	
	@Transactional(readOnly = true)
	public Comentario findCommentById(int id) throws DataAccessException {
		return comentarioRepository.findById(id).get();
	}
	
	@Transactional
	public void guardarComentario(Comentario comentario, Cliente cliente, Integer articuloId) {
		comentario.setArticulo(articuloService.findArticuloById(articuloId));
		comentario.setCliente(cliente);
		comentarioRepository.save(comentario);
	}
	
	@Transactional
	public List<Comentario> getComentariosById(Integer articuloId) {
		return comentarioRepository.findByArticulo(articuloId);
	}
	
}
