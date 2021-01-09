package org.springframework.samples.dpc.service;

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
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		Cesta c = new Cesta();
		LineaCesta linea1 = new LineaCesta();
		linea1.setCantidad(1);
		linea1.setCesta(c);
		linea1.setId(1);
		linea1.setArticulo(articuloService.findArticuloById(1));
		LineaCesta linea2 = new LineaCesta();
		linea2.setCantidad(1);
		linea2.setCesta(c);
		linea2.setId(2);
		linea2.setArticulo(articuloService.findArticuloById(6));
		List<LineaCesta> lineas = new ArrayList<LineaCesta>();
		lineas.add(linea1);
		lineas.add(linea2);
		c.setLineas(lineas);

		Pedido p = new Pedido();
		p.setFecha(LocalDate.now());
		p.setCliente(clienteRepository.findByDni("23456789"));

		LineaPedido lp1 = new LineaPedido();
		lp1.setCantidad(1);
		lp1.setPedido(p);
		lp1.setId(1);
		lp1.setArticulo(articuloService.findArticuloById(1));
		lp1.setPrecioUnitario(988.99);
		LineaPedido lp2 = new LineaPedido();
		lp2.setCantidad(1);
		lp2.setPedido(p);
		lp2.setId(2);
		lp2.setArticulo(articuloService.findArticuloById(6));
		lp2.setPrecioUnitario(350.0);
		p.setPrecioTotal(1338.99);
		List<LineaPedido> lineas2 = new ArrayList<LineaPedido>();
		p.setLineas(lineas2);

		this.lineaPedidoService.crearLinea(p, linea1);

		assertThat(linea1.getCantidad()).isEqualTo(lp1.getCantidad());
		assertThat(linea1.getArticulo()).isEqualTo(lp1.getArticulo());

	}
}
