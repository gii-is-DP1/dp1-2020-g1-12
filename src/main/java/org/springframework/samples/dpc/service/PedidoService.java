package org.springframework.samples.dpc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.PedidoRepository;
import org.springframework.samples.dpc.service.exceptions.PedidoNoValidoException;
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
	private MensajeService mensajeService;
	private VendedorService vendedorService;

	@Autowired
	public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService,
			LineaPedidoService lineaPedidoService, CestaService cestaService,ArticuloService articuloService,
			TarjetaService tarjetaService, MensajeService mensajeService, VendedorService vendedorService) {
		this.pedidoRepository = pedidoRepository;
		this.clienteService = clienteService;
		this.lineaPedidoService = lineaPedidoService;
		this.cestaService = cestaService;
		this.articuloService = articuloService;
		this.tarjetaService = tarjetaService;
		this.mensajeService = mensajeService;
		this.vendedorService = vendedorService;
	}

	public Page<Pedido> obtenerPedidos(Integer page, Integer size, String orden) {
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "pedidos");
		return pedidoRepository.findByCliente(clienteService.obtenerIdSesion(), pageable);
	}

	public Pedido obtenerPedido(Integer pedidoId) {
		return pedidoRepository.findById(pedidoId).isPresent() ? pedidoRepository.findById(pedidoId).get() : null;
	}

	@Transactional(rollbackFor=PedidoNoValidoException.class)
	public void tramitarPedido(Integer tarjetaId) throws PedidoNoValidoException {
		Optional<LineaCesta> res = cestaService.obtenerCestaCliente().getLineas().stream().filter(x->x.getArticulo().getStock()<x.getCantidad()).findFirst();
		if(res.isPresent()) {
			throw new PedidoNoValidoException();
		}
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

	public Pair<List<LineaPedido>, List<Integer>> obtenerLineas(Integer pedidoId) {
		List<LineaPedido> lineas = lineaPedidoService.obtenerLineas(pedidoId);
		List<Integer> contadores = new ArrayList<>();
		lineas.stream().forEach(x -> contadores.add(mensajeService.getMensajesNoLeidosCliente(clienteService.getClienteDeSesion(), 
				vendedorService.vendedorDeUnArticulo(x.getArticulo().getId())))); 
		return Pair.of(lineas, contadores);
	}
	
	public Double obtenerGastoEnvio(Pedido pedido, List<LineaPedido> lineas) {
		Double result = lineas.stream().collect(Collectors.summarizingDouble(l -> l.getPrecioUnitario() * l.getCantidad())).getSum();
		return pedido.getPrecioTotal() - result;
	}
}