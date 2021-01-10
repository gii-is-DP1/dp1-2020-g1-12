package org.springframework.samples.dpc.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.repository.LineaCestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineaCestaService {
	
	private LineaCestaRepository lineaCestaRepository;
	private ArticuloService articuloService;
	
	public LineaCestaService(LineaCestaRepository lineaCestaRepository, ArticuloService articuloService) {
		this.lineaCestaRepository = lineaCestaRepository;
		this.articuloService = articuloService;
	}
	
	@Transactional
	public void crearLinea(Integer articuloId,Cesta cesta) {
		Articulo articulo = articuloService.findArticuloById(articuloId);
		LineaCesta lineaCesta = new LineaCesta();
		lineaCesta.setArticulo(articulo);
		lineaCesta.setCantidad(1); //Añadimos por defecto 1 unidad por producto, en caso de variar se haría en la propia cesta
		lineaCesta.setCesta(cesta);
		lineaCestaRepository.save(lineaCesta);
	}
	
	@Transactional
	public LineaCesta findLineaById(Integer lineaId) {
		Optional<LineaCesta> c = lineaCestaRepository.findById(lineaId);
		return c.isPresent() ? c.get() : null;
	}
	
	@Transactional
	public void eliminarLinea(LineaCesta lineaCesta) {
		lineaCestaRepository.delete(lineaCesta);
	}
	
	@Transactional
	public Long getTiempoEntrega(Cesta cesta) {
		return cesta.getLineas().stream().collect(Collectors.summarizingInt(x -> x.getArticulo().getTiempoEntrega())).getSum();
	}
}
