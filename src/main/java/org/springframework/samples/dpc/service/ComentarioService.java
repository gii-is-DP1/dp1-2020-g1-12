package org.springframework.samples.dpc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.repository.ComentarioRepository;
import org.springframework.samples.dpc.service.exceptions.ComentarioProhibidoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

	private ComentarioRepository comentarioRepository;
	private ArticuloService articuloService;
	private UserService userService;
	private ModeradorService moderadorService;
	private VendedorService vendedorService;
	private ClienteService clienteService;
	private LineaPedidoService lineaPedidoService;

	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository, ArticuloService articuloService,
			UserService userService, ModeradorService moderadorService, VendedorService vendedorService,
			ClienteService clienteService, LineaPedidoService lineaPedidoService) {
		this.comentarioRepository = comentarioRepository;
		this.articuloService = articuloService;
		this.userService = userService;
		this.moderadorService = moderadorService;
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
		this.lineaPedidoService = lineaPedidoService;
	}

	@Transactional(readOnly = true)
	public Comentario findCommentById(int id) throws DataAccessException {
		return (comentarioRepository.findById(id).isPresent()) ? comentarioRepository.findById(id).get() : null;
	}

	@Transactional()
	public void eliminarComentario(Comentario comentario) throws DataAccessException {
		articuloService.eliminarComentario(comentario.getArticulo(), comentario);
		comentarioRepository.delete(comentario);
	}

	@Transactional(rollbackFor = ComentarioProhibidoException.class)
	public void editar(Comentario comentario, Integer id, Integer articuloId) throws ComentarioProhibidoException {
		Comentario comentarioGuardado = findCommentById(id);
		List<Integer> articulos = new ArrayList<>();
		articulos.add(articuloId);
		String autoridad = userService.getAuthority();
		Articulo articulo = articuloService.findArticuloById(articuloId);

		if (autoridad.equals("cliente")) {
			if (lineaPedidoService.esComprador(articulos, clienteService.obtenerIdSesion())) {
				if (comentario.getValoracion() == 0) { // Poner a 1 el comentario si envía un 0 al
					comentario.setValoracion(1); // inspeccionar elemento con el navegador
				}
				comentario.setArticulo(articulo);
				comentario.setCliente(clienteService.getClienteDeSesion());
			} else {
				throw new ComentarioProhibidoException();
			}
		} else {
			if (vendedorService.esVendedorDelArticulo(articuloId)) {
				comentario.setValoracion(0);
				comentario.setArticulo(articulo);
				comentario.setVendedor(vendedorService.getVendedorDeSesion());
			} else {
				throw new ComentarioProhibidoException();
			}
		}
		comentarioGuardado.setDescripcion(comentario.getDescripcion());
		comentarioGuardado.setValoracion(comentario.getValoracion());
	}

	@Transactional
	public Boolean puedeComentar(Integer articuloId) {
		String autoridad = userService.getAuthority();
		List<Integer> articulos = new ArrayList<>();
		articulos.add(articuloId);

		return !autoridad.equals("anonymous")
				&& (autoridad.equals("cliente") && lineaPedidoService.esComprador(articulos,
				clienteService.obtenerIdSesion()) || autoridad.equals("moderador")
				|| (autoridad.equals("vendedor") && vendedorService.esVendedorDelArticulo(articuloId)));
	}

	@Transactional
	public Integer puedeEditarCliente(Integer articuloId) {
		return clienteService.obtenerIdSesion();
	}

	@Transactional
	public Integer puedeEditarVendedor(Integer comentarioId) {
		return vendedorService.obtenerIdSesion();
	}

	@Transactional(rollbackFor = ComentarioProhibidoException.class)
	public void guardarComentario(Comentario comentario, Integer articuloId) throws ComentarioProhibidoException {
		String autoridad = userService.getAuthority();
		Articulo articulo = articuloService.findArticuloById(articuloId);
		List<Integer> articulos = new ArrayList<>();
		articulos.add(articuloId);

		if (autoridad.equals("moderador")) {
			comentario.setValoracion(0);
			comentario.setArticulo(articulo);
			comentario.setModerador(moderadorService.getModeradorDeSesion());
		} else if (autoridad.equals("cliente")) {
			if (lineaPedidoService.esComprador(articulos, clienteService.obtenerIdSesion())) {
				if (comentario.getValoracion() == 0) { // Poner a 1 el comentario si envía un 0 al
					comentario.setValoracion(1); // inspeccionar elemento con el navegador
				}
				comentario.setArticulo(articulo);
				comentario.setCliente(clienteService.getClienteDeSesion());
			} else {
				throw new ComentarioProhibidoException();
			}
		} else {
			if (vendedorService.esVendedorDelArticulo(articuloId)) {
				comentario.setValoracion(0);
				comentario.setArticulo(articulo);
				comentario.setVendedor(vendedorService.getVendedorDeSesion());
			} else {
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
				.collect(Collectors.averagingDouble(Comentario::getValoracion));
	}

}
