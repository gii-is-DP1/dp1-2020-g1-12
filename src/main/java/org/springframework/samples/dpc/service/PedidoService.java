package org.springframework.samples.dpc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService {

	private PedidoRepository pedidoRepository;
	private ClienteService clienteService;
	private LineaPedidoService lineaPedidoService;
	private CestaService cestaService;
	
	@Autowired
	public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService, 
			LineaPedidoService lineaPedidoService, CestaService cestaService) {
		this.pedidoRepository = pedidoRepository;
		this.clienteService = clienteService;
		this.lineaPedidoService = lineaPedidoService;
		this.cestaService = cestaService;
	}

	public Page<Pedido> obtenerPedidos(Integer page, Integer size, String orden) {
		Pageable pageable = obtenerFiltros(page, size, orden);
		return pedidoRepository.findByCliente(clienteService.obtenerIdSesion(), pageable);
	}
	
	public Pedido obtenerPedido(Integer pedidoId) {
		return pedidoRepository.findById(pedidoId).isPresent() ? pedidoRepository.findById(pedidoId).get() : null;
	}
	
	@Transactional
	public void tramitarPedido() {
		Pedido pedido = new Pedido();
		pedido.setCliente(clienteService.getClienteDeSesion());
		pedido.setFecha(LocalDate.now());
		pedido.setPrecioTotal(cestaService.obtenerCestaCliente().getPrecioFinal());
		pedidoRepository.save(pedido);
		
		List<LineaCesta> lineas = cestaService.obtenerCestaCliente().getLineas();
		for(int i = 0; i < lineas.size(); i++) {
			lineaPedidoService.crearLinea(pedido, lineas.get(i));
		}
		cestaService.eliminarLineasCesta(lineas);
	}
	
	public List<LineaPedido> obtenerLineas(Integer pedidoId) {
		return lineaPedidoService.obtenerLineas(pedidoId);
	}

	@Transactional(readOnly = true)
	public Pageable obtenerFiltros(Integer page, Integer size, String orden) throws ResponseStatusException{
		if(!orden.equals("id") && !orden.equals("-id") && !orden.equals("fecha") && !orden.equals("-fecha")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parámetro de búsqueda introducido no "
					+ "válido.");
		}
		Order order = orden.startsWith("-") ? new Order(Sort.Direction.DESC, orden.replace("-", "")) :
			new Order(Sort.Direction.ASC, orden);
	
		return PageRequest.of(page, size, Sort.by(order));
	}
}