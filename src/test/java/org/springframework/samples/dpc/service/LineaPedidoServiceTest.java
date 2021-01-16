package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.model.LineaPedido;
import org.springframework.samples.dpc.model.Pedido;
import org.springframework.samples.dpc.repository.ClienteRepository;
import org.springframework.samples.dpc.repository.LineaCestaRepository;
import org.springframework.samples.dpc.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LineaPedidoServiceTest {

	private LineaPedidoRepository lineaPedidoRepository;
	private ArticuloService articuloService;
	private LineaPedidoService lineaPedidoService;
	private LineaCestaRepository lineaCestaRepository;
	private ClienteRepository clienteRepository;

	@Autowired
	public LineaPedidoServiceTest(LineaPedidoRepository lineaPedidoRepository, ArticuloService articuloService,
			LineaCestaRepository lineaCestaRepository, LineaPedidoService lineaPedidoService,
			ClienteRepository clienteRepository) {
		this.lineaPedidoRepository = lineaPedidoRepository;
		this.articuloService = articuloService;
		this.lineaPedidoService = lineaPedidoService;
		this.lineaCestaRepository = lineaCestaRepository;
		this.clienteRepository = clienteRepository;
	}

	@Test
	void testCrearLinea() {

		List<LineaCesta> lcl = new ArrayList<>();
		Cesta c = new Cesta();
		LineaCesta lc = new LineaCesta();
		lc.setId(1);
		lc.setCesta(c);
		lc.setCantidad(2);
		lc.setArticulo(articuloService.findArticuloById(1));
		lcl.add(lc);
		c.setId(1);
		c.setLineas(lcl);

		List<LineaPedido> lpl = new ArrayList<>();
		Pedido p = new Pedido();
//		LineaPedido lp = new LineaPedido();
//		lp.setId(1);
//		lp.setPedido(p);
//		lp.setCantidad(2);
//		lp.setPrecioUnitario(500.);
//		lp.setArticulo(articuloService.findArticuloById(1));
//		lpl.add(lp);
		p.setId(1);
		Double pt = 1000.;
		p.setPrecioTotal(pt);
		p.setFecha(LocalDate.now());
		p.setLineas(lpl);
		p.setCliente(clienteRepository.findByDni("23456789"));

		this.lineaPedidoService.crearLinea(p, lc);

		assertThat(lc.getCantidad()).isEqualTo(lineaPedidoRepository.findByPedido(1).get(0).getCantidad());
		assertThat(lc.getArticulo()).isEqualTo(lineaPedidoRepository.findByPedido(1).get(0).getArticulo());

	}
}
