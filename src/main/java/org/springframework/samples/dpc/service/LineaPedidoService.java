package org.springframework.samples.dpc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineaPedidoService {

	private final LineaPedidoRepository lineaPedidoRepository;
	private final ArticuloService articuloService;

	@Autowired
	public LineaPedidoService(LineaPedidoRepository lineaPedidoRepository, ArticuloService articuloService) {
		this.lineaPedidoRepository = lineaPedidoRepository;
		this.articuloService = articuloService;
	}

	@Transactional
	public void crearLinea(Pedido pedido, LineaCesta lineaCesta) {
		LineaPedido lineaPedido = new LineaPedido();
		lineaPedido.setArticulo(lineaCesta.getArticulo());
		lineaPedido.setCantidad(lineaCesta.getCantidad());
		lineaPedido.setPrecioUnitario(lineaCesta.getArticulo().getPrecio());
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
}
