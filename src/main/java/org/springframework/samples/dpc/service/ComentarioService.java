package org.springframework.samples.dpc.service;

import java.util.List;

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
	
	@Transactional
	public Boolean puedeComentar(Integer articuloId) {
		String autoridad = userService.getAuthority();

		return !autoridad.equals("anonymous") && (autoridad.equals("cliente") || autoridad.equals("moderador") || 
				(autoridad.equals("vendedor") && vendedorService.vendedorDeUnArticulo(articuloId).
						equals((vendedorService.findSellerById(vendedorService.obtenerIdSesion())))));
	}
	
	@Transactional
	public void guardarComentario(Comentario comentario, Integer articuloId) 
				throws ComentarioProhibidoException {
		String autoridad = userService.getAuthority();
		if(autoridad.equals("moderador")) {
			comentario.setArticulo(articuloService.findArticuloById(articuloId));
			comentario.setModerador(moderadorService.getModeradorDeSesion());
		}
		else if(autoridad.equals("cliente")) {
			comentario.setArticulo(articuloService.findArticuloById(articuloId));
			comentario.setCliente(clienteService.getClienteDeSesion());			
		}
		else {
			if(vendedorService.esVendedorDelArticulo(articuloId)) {
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
	public List<Comentario> getComentariosById(Integer articuloId) {
		return comentarioRepository.findByArticulo(articuloId);
	}
	
}
