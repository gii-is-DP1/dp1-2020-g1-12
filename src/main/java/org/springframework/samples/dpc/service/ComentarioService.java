package org.springframework.samples.dpc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {
	
	private final ComentarioRepository comentarioRepository;

	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository) {
		this.comentarioRepository = comentarioRepository;
	}
	
	@Transactional
	public void guardarComentario(Comentario comentario) {
		comentarioRepository.save(comentario);
	}
	
	@Transactional
	public List<Comentario> getComentariosById(Integer articuloId) {
		return comentarioRepository.findByArticulo(articuloId);
	}
}
