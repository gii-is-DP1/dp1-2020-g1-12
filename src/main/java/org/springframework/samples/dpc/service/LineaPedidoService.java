package org.springframework.samples.dpc.service;

import java.util.List;

import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineaPedidoService {

	private LineaPedidoRepository lineaPedidoRepository;
	
	public LineaPedidoService(LineaPedidoRepository lineaPedidoRepository) {
		this.lineaPedidoRepository = lineaPedidoRepository;
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
}
