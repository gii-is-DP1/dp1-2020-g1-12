package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Tipo;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ArticuloServiceTest {

	@Autowired 
	private ArticuloService articuloService;
	
	@Test
	void testSave() {
		Articulo a = new Articulo();
		a.setUrlImagen("/");
		a.setPrecio(10.99);
		a.setStock(200);
		a.setTipo(Tipo.Nuevo);
		a.setGastoEnvio(2.99);
		a.setTiempoEntrega(20);
		a.setModelo("ABCD");
		a.setMarca("BCDE");
		Oferta oferta = new Oferta();
		oferta.setId(1);
		oferta.setPorcentaje(5);
		oferta.setDisponibilidad(true);
		
		a.setOferta(oferta);
		this.articuloService.guardarArticulo(a);
		
		Integer id = a.getId();
		Articulo articulo = this.articuloService.findArticuloById(id);
		assertEquals(a, articulo);
	}
	
	@Test
	void testArticulosEnVentaByProvider() {
		List<Articulo> articulos1 = this.articuloService.articulosEnVentaByProvider(1);
		assertThat(articulos1.size()).isEqualTo(2);
		
		List<Articulo> articulos2 = this.articuloService.articulosEnVentaByProvider(10);
		assertThat(articulos2.isEmpty());
	}
}
