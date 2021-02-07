package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.repository.ClienteRepository;
import org.springframework.samples.dpc.repository.LineaCestaRepository;
import org.springframework.samples.dpc.repository.LineaPedidoRepository;
import org.springframework.samples.dpc.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class LineaPedidoServiceTest {

	private LineaPedidoRepository lineaPedidoRepository;
	private LineaPedidoService lineaPedidoService;
	private LineaCestaRepository lineaCestaRepository;
	private ClienteRepository clienteRepository;
	private PedidoRepository pedidoRepository;
	private TarjetaService tarjetaService;

	@Autowired
	public LineaPedidoServiceTest(LineaPedidoRepository lineaPedidoRepository, LineaCestaRepository lineaCestaRepository,
			LineaPedidoService lineaPedidoService, ClienteRepository clienteRepository,
			PedidoRepository pedidoRepository,TarjetaService tarjetaService) {
		this.lineaPedidoRepository = lineaPedidoRepository;
		this.lineaPedidoService = lineaPedidoService;
		this.lineaCestaRepository = lineaCestaRepository;
		this.clienteRepository = clienteRepository;
		this.pedidoRepository = pedidoRepository;
		this.tarjetaService = tarjetaService;
	}

	@Test
	@DisplayName("Test Crear una linea de pedido")
	void testCrearLinea() {

		Cesta c = new Cesta();
		List<LineaCesta> lc = lineaCestaRepository.findByCesta(1);
		c.setLineas(lc);

		List<LineaPedido> lpl = new ArrayList<>();
		Pedido p = new Pedido();
		Double pt = 1688.99;
		TarjetaCredito tarjeta = tarjetaService.findTarjetaById(1);
		p.setTarjeta(tarjeta);
		p.setPrecioTotal(pt);
		p.setFecha(LocalDate.now());
		p.setLineas(lpl);
		p.setCliente(clienteRepository.findByDni("23456789"));
		this.pedidoRepository.save(p);
		this.lineaPedidoService.crearLinea(p, lc.get(0));

		assertThat(lc.get(0).getCantidad()).isEqualTo(lineaPedidoRepository.findByPedido(2).get(0).getCantidad());
		assertThat(lc.get(0).getArticulo()).isEqualTo(lineaPedidoRepository.findByPedido(2).get(0).getArticulo());

	}
}
