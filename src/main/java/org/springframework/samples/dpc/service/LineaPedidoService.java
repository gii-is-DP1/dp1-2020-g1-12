package org.springframework.samples.dpc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Estado;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineaPedidoService {

	private LineaPedidoRepository lineaPedidoRepository;
	private ArticuloService articuloService;
	private VendedorService vendedorService;


	@Autowired
	public LineaPedidoService(LineaPedidoRepository lineaPedidoRepository, ArticuloService articuloService, VendedorService vendedorService) {
		this.lineaPedidoRepository = lineaPedidoRepository;
		this.articuloService = articuloService;
		this.vendedorService = vendedorService;
	}

	@Transactional
	public void crearLinea(Pedido pedido, LineaCesta lineaCesta) {
		LineaPedido lineaPedido = new LineaPedido();
		lineaPedido.setArticulo(lineaCesta.getArticulo());
		lineaPedido.setEstado(Estado.Pendiente);
		lineaPedido.setCantidad(lineaCesta.getCantidad());
		if(lineaCesta.getArticulo().getOferta().isDisponibilidad()) {
			Articulo articulo = lineaCesta.getArticulo();
			lineaPedido.setPrecioUnitario(articulo.getPrecio() - (articulo.getOferta().getPorcentaje() * articulo.getPrecio()) / 100);
		} else {
			lineaPedido.setPrecioUnitario(lineaCesta.getArticulo().getPrecio());
		}
		lineaPedido.setPedido(pedido);
		lineaPedidoRepository.save(lineaPedido);
	}

	public List<LineaPedido> obtenerLineas(Integer pedidoId) {
		return lineaPedidoRepository.findByPedido(pedidoId);
	}
	
	@Transactional(readOnly = true)
	public Page<LineaPedido> articulosVendidosByProvider(Integer page, Integer size, String orden, 
			Integer idVendedor) {
		List<Integer> articulosVendedor = articuloService.articulosByProvider(idVendedor).stream().
				map(Articulo::getId).collect(Collectors.toList());
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "vendidos");
		return lineaPedidoRepository.findArticulosVendidos(articulosVendedor, pageable);
	}
	
	@Transactional(readOnly = true)
	public Boolean articuloComprado(Integer articuloId) {
		Pageable pageable = articuloService.obtenerFiltros(0, Integer.MAX_VALUE, "-id", "vendidos");
		List<Integer> ids = new ArrayList<>();
		ids.add(articuloId);
		return lineaPedidoRepository.findArticulosVendidos(ids, pageable).hasContent();		
	}
	
	@Transactional(readOnly = true)
	public LineaPedido obtenerLineaPedido(Integer lineaPedidoId) {
		Optional<LineaPedido> optional = lineaPedidoRepository.findById(lineaPedidoId);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	@Transactional()
	public void actualizarEstado(Integer lineaPedidoId, LineaPedido lineaPedido) {
		LineaPedido lineaOriginal = obtenerLineaPedido(lineaPedidoId);
		if(lineaOriginal != null) {
			lineaOriginal.setEstado(lineaPedido.getEstado());
		}
	}

	@Transactional(readOnly = true)
	public boolean compruebaVendedorLinea(Integer lineaPedidoId) {
		return vendedorService.esVendedorDelArticulo(obtenerLineaPedido(lineaPedidoId).getArticulo().getId());
	}
	
	@Transactional(readOnly = true)
	public boolean esComprador(List<Integer> lineas, Integer clienteId) {
		return lineaPedidoRepository.esComprador(lineas, clienteId) > 0 ? true: false;
	}
}
