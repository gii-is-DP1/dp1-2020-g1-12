package org.springframework.samples.dpc.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.stereotype.Service;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.service.exceptions.CantidadNoValidaCestaException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CestaServiceTest {

	private CestaService cestaService;
	private ArticuloService articuloService;

	@Autowired
	public CestaServiceTest(CestaService cestaService, ArticuloService articuloService) {
		this.cestaService = cestaService;
		this.articuloService = articuloService;
	}

	@Test
	void testCrear() {

		Cesta c = new Cesta();
		LineaCesta linea1 = new LineaCesta();
		linea1.setCantidad(1);
		linea1.setCesta(c);
		linea1.setId(1);
		linea1.setArticulo(articuloService.findArticuloById(1));
		LineaCesta linea2 = new LineaCesta();
		linea2.setCantidad(2);
		linea2.setCesta(c);
		linea2.setId(2);
		linea2.setArticulo(articuloService.findArticuloById(6));
		List<LineaCesta> lineas = new ArrayList<LineaCesta>();
		lineas.add(linea1);
		lineas.add(linea2);
		c.setLineas(lineas);

		this.cestaService.crearCesta(c);
		Cesta c2 = this.cestaService.findCestaById(1);
		assertThat(c).isEqualTo(c2);
	}

	@Test
	void testObtenerCestaCliente() {
		// ¿Cómo se hace si obtiene el id de sesión?
	}

	@Test
	void testEliminarLineasCesta() {
		Cesta c = this.cestaService.findCestaById(1);
		this.cestaService.eliminarLineasCesta(c.getLineas());
		assertThat(c.getLineas().size()).isZero();
	}

	@Test
	void testAnyadirLineaCesta() {
		Articulo a = articuloService.findArticuloById(2);
		this.cestaService.anyadirLineaCesta(1);
		assertThat(this.cestaService.articuloEnCesta(2)).isEqualTo(a);
	}

	@Test
	void testLineasCesta() {
		assertThat(this.cestaService.obtenerCestaCliente().getLineas().size())
				.isEqualTo(this.cestaService.lineasCesta());
	}

	@Test
	void testActualizarCesta() throws CantidadNoValidaCestaException {
		Cesta c2 = this.cestaService.findCestaById(1);
		Cesta c = this.cestaService.findCestaById(1);
		LineaCesta linea3 = new LineaCesta();
		linea3.setCantidad(2);
		linea3.setCesta(c);
		linea3.setId(2);
		linea3.setArticulo(articuloService.findArticuloById(2));
		List<LineaCesta> lineas = new ArrayList<LineaCesta>();
		lineas.add(linea3);
		c.setLineas(lineas);
		this.cestaService.actualizarCesta(c);
		assertThat(c).isNotEqualTo(c2);
	}
}
