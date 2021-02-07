package org.springframework.samples.dpc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.repository.ArticuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ArticuloService {

	private ArticuloRepository articuloRepository;
	private static final String cadenaArticulo = "articulo";
	
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
		Articulo articulo = findArticuloById(articuloId);
		articulo.setStock(0);
	}

	@Transactional
	public void eliminarComentario(Articulo articulo, Comentario comentario) {
		articulo.getComentarios().remove(comentario);
	}
	

	@Transactional
	public Page<Articulo> articulosEnVentaByProvider(Integer id, Integer page, Integer size, String orden) {
		Pageable pageable = obtenerFiltros(page, size, orden, cadenaArticulo);
		return articuloRepository.articulosEnVentaPorId(id, pageable);
	}

	@Transactional
	public List<Articulo> articulosByProvider(Integer id) {
		return articuloRepository.articulosDeUnVendedor(id);
	}

	@Transactional(readOnly = true)
	public Articulo findArticuloById(int id) throws DataAccessException {
		return (articuloRepository.findById(id).isPresent()) ? articuloRepository.findById(id).get() : null;
	}

	@Transactional(readOnly = true)
	public Page<Articulo> articulosDisponibles(Integer page, Integer size, String orden) {
		Pageable pageable = obtenerFiltros(page, size, orden, cadenaArticulo);
		return articuloRepository.articulosDisponibles(pageable);
	}

	@Transactional(readOnly = true)
	public List<Articulo> articulosOfertados() {
		return articuloRepository.articulosOfert();
	}

	@Transactional(readOnly = true)
	public List<Articulo> ofertasRandomAcotada() {
		List<Articulo> ofertas = articulosOfertados();
		Collections.shuffle(ofertas);
		if (ofertas.size() > 5) {
			ofertas = ofertas.subList(0, 5);
		}

		return ofertas;
	}

	@Transactional(readOnly = true)
	public List<Articulo> articulosRelacionados(Articulo articulo) {
		Pageable pageable = obtenerFiltros(0, (int) articuloRepository.count(), "-id", cadenaArticulo);
		List<Articulo> relacionados = new ArrayList<>();
		List<Articulo> articulos = articuloRepository.articulosDisponibles(pageable).getContent();
		for (Articulo art : articulos) {
			if (relacionados.size() < 6 && !(art.getId().equals(articulo.getId()))
					&& articulo.getGeneros().containsAll(art.getGeneros()))
				relacionados.add(art);
		}
		return relacionados;
	}

	@Transactional(readOnly = true)
	public Page<Articulo> busqueda(Articulo articulo, Integer page, Integer size, String orden) {
		Pageable pageable = obtenerFiltros(page, size, orden, cadenaArticulo);
		String busqueda = articulo.getModelo();
		if (articulo.getGeneros() == null) {
			return articuloRepository.articulosPorNombre(busqueda, pageable);
		} else if (busqueda.isEmpty()) {
			return articuloRepository.articulosPorGenero(
					articulo.getGeneros().stream().map(Genero::getId).collect(Collectors.toList()), pageable);
		} else {
			return articuloRepository.articulosPorGeneroNombre(
					articulo.getGeneros().stream().map(Genero::getId).collect(Collectors.toList()), busqueda, pageable);
		}

	}

	@Transactional(readOnly = true)
	public String mensajeDeBusqueda(Articulo articulo) {
		String mensaje = "Resultados de la búsqueda ";
		if (articulo.getGeneros() == null) {
			mensaje += "'" + articulo.getModelo() + "':";
		} else if (articulo.getModelo().isEmpty()) {
			mensaje += "con los géneros seleccionados:";
		} else {
			mensaje += "'" + articulo.getModelo() + "'" + " con los géneros seleccionados:";
		}
		return mensaje;
	}

	@Transactional(readOnly = true)
	public Pageable obtenerFiltros(Integer page, Integer size, String orden, String caso) throws ResponseStatusException {
		String respuesta = "Parámetro de búsqueda introducido no válido.";
		switch (caso) {
		case cadenaArticulo:
			if (!orden.equals("id") && !orden.equals("-id") && !orden.equals("marca") && !orden.equals("-marca")
					&& !orden.equals("precio") && !orden.equals("-precio")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						respuesta);
			}
			page = page < 0 ? 0 : page;
			size = size < 10 ? 10 : size;
			break;
		case "clientes":
			if(!orden.equals("nombre") && !orden.equals("-nombre") && !orden.equals("apellido") && !orden.equals("-apellido") &&
					!orden.equals("dni") && !orden.equals("-dni")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, respuesta);
			}
			page = page < 0 ? 0 : page;
			size = size < 5 ? 5 : size;
			break;
		case "pedidos":
			if (!orden.equals("id") && !orden.equals("-id") && !orden.equals("fecha") && !orden.equals("-fecha")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, respuesta);
			}
			page = page < 0 ? 0 : page;
			size = size < 2 ? 2 : size;
			break;
		case "vendidos":
			if (!orden.equals("id") && !orden.equals("-id")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, respuesta);
			}
			page = page < 0 ? 0 : page;
			size = size < 10 ? 10 : size;
			break;
		}
		Order order = orden.startsWith("-") ? new Order(Sort.Direction.DESC, orden.replace("-", ""))
				: new Order(Sort.Direction.ASC, orden);
		return PageRequest.of(page, size, Sort.by(order));
	}
}
