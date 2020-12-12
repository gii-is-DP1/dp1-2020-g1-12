package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ArticuloServiceTest {

	private final Integer ARTICULO_ID = 1;
	
	@Autowired 
	private ArticuloService articuloService;
	
	@Test
	void testSave() {
		Articulo a = new Articulo();
		a.setUrlImagen("https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15/intel-Visual-Basic.png");
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
		List<Articulo> articulos1 = this.articuloService.articulosEnVentaByProvider(ARTICULO_ID);
		assertThat(articulos1.size()).isEqualTo(2);
		
		List<Articulo> articulos2 = this.articuloService.articulosEnVentaByProvider(10);
		assertThat(articulos2.isEmpty());
	}
	
	@Test
	void testEliminarArticulo() {
		Articulo articulo = articuloService.findArticuloById(ARTICULO_ID);
		assertThat(articulo.getStock()).isGreaterThan(0);
		
		articuloService.eliminarArticulo(ARTICULO_ID);
		assertThat(articulo.getStock()).isEqualTo(0);
	}
}
