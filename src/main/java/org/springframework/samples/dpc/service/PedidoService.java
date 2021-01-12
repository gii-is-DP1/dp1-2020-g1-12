package org.springframework.samples.dpc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	private PedidoRepository pedidoRepository;
	private ClienteService clienteService;
	private LineaPedidoService lineaPedidoService;
	private CestaService cestaService;
	private ArticuloService articuloService;
	private TarjetaService tarjetaService;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService,
			LineaPedidoService lineaPedidoService, CestaService cestaService,ArticuloService articuloService,
			TarjetaService tarjetaService) {
		this.pedidoRepository = pedidoRepository;
		this.clienteService = clienteService;
		this.lineaPedidoService = lineaPedidoService;
		this.cestaService = cestaService;
		this.articuloService = articuloService;
		this.tarjetaService = tarjetaService;
	}

	public Page<Pedido> obtenerPedidos(Integer page, Integer size, String orden) {
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "pedidos");
		return pedidoRepository.findByCliente(clienteService.obtenerIdSesion(), pageable);
	}

	public Pedido obtenerPedido(Integer pedidoId) {
		return pedidoRepository.findById(pedidoId).isPresent() ? pedidoRepository.findById(pedidoId).get() : null;
	}

	@Transactional
	public void tramitarPedido(Integer tarjetaId) {
		Pedido pedido = new Pedido();
		pedido.setCliente(clienteService.getClienteDeSesion());
		pedido.setFecha(LocalDate.now());
		pedido.setPrecioTotal(cestaService.obtenerCestaCliente().getPrecioFinal());
		pedido.setTarjeta(tarjetaService.findTarjetaById(tarjetaId));
		pedidoRepository.save(pedido);

		List<LineaCesta> lineas = cestaService.obtenerCestaCliente().getLineas();
		for (int i = 0; i < lineas.size(); i++) {
			lineaPedidoService.crearLinea(pedido, lineas.get(i));
			lineas.get(i).getArticulo().setStock(lineas.get(i).getArticulo().getStock() - lineas.get(i).getCantidad());

		}
		cestaService.eliminarLineasCesta(lineas);
	}

	public List<LineaPedido> obtenerLineas(Integer pedidoId) {
		return lineaPedidoService.obtenerLineas(pedidoId);
	}
}