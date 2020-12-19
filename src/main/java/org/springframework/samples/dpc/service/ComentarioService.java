package org.springframework.samples.dpc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.repository.ComentarioRepository;
import org.springframework.samples.dpc.service.exceptions.ComentarioProhibidoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {
	
	private final ComentarioRepository comentarioRepository;
	private final ArticuloService articuloService;
	private final UserService userService;
	private final ModeradorService moderadorService;
	private final VendedorService vendedorService;
	private final ClienteService clienteService;


	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository, ArticuloService articuloService, 
			UserService userService, ModeradorService moderadorService, VendedorService vendedorService, 
			ClienteService clienteService) {
		this.comentarioRepository = comentarioRepository;
		this.articuloService = articuloService;
		this.userService = userService ;
		this.moderadorService = moderadorService;
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
	}
	
	@Transactional(readOnly = true)
	public Comentario findCommentById(int id) throws DataAccessException {
		return comentarioRepository.findById(id).get();
	}
	
	@Transactional()
	public void eliminarComentario(Comentario comentario) throws DataAccessException {
		articuloService.eliminarComentario(comentario.getArticulo(), comentario);
		comentarioRepository.delete(comentario);
	}
	
	@Transactional
	public Boolean puedeComentar(Integer articuloId) {
		String autoridad = userService.getAuthority();

		return !autoridad.equals("anonymous") && (autoridad.equals("cliente") || autoridad.equals("moderador") || 
				(autoridad.equals("vendedor") && vendedorService.esVendedorDelArticulo(articuloId)));
	}
	
	@Transactional(rollbackFor = ComentarioProhibidoException.class)
	public void guardarComentario(Comentario comentario, Integer articuloId) 
				throws ComentarioProhibidoException {
		String autoridad = userService.getAuthority();
		if(autoridad.equals("moderador")) {
			comentario.setValoracion(0);
			comentario.setArticulo(articuloService.findArticuloById(articuloId));
			comentario.setModerador(moderadorService.getModeradorDeSesion());
		}
		else if(autoridad.equals("cliente")) {
			if(comentario.getValoracion() == 0) {			// Poner a 1 el comentario si envía un 0 al
				comentario.setValoracion(1);				// inspeccionar elemento con el navegador
			}
			comentario.setArticulo(articuloService.findArticuloById(articuloId));
			comentario.setCliente(clienteService.getClienteDeSesion());			
		}
		else {
			if(vendedorService.esVendedorDelArticulo(articuloId)) {
				comentario.setValoracion(0);
				comentario.setArticulo(articuloService.findArticuloById(articuloId));
				comentario.setVendedor(vendedorService.getVendedorDeSesion());
			}
			else {
				throw new ComentarioProhibidoException();
			}
		}
		comentarioRepository.save(comentario);
	}
	
	@Transactional
	public List<Comentario> getComentariosDeUnArticulo(Integer articuloId) {
		return comentarioRepository.findByArticulo(articuloId);
	}
	
	
	@Transactional
	public Double getValoracionDeUnArticulo(Integer articuloId) {
		return getComentariosDeUnArticulo(articuloId).stream().filter(x -> x.getValoracion() != 0)
				.collect(Collectors.averagingDouble(x -> x.getValoracion()));
	}
	
}
