package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ArticuloServiceTest {

	private final Integer ARTICULO_ID = 1;
	private final Integer VENDEDOR_ID = 1;
	
	private ArticuloService articuloService;
	private ArticuloRepository articuloRepository;
	private GeneroService generoService;
	
	@Autowired
	public ArticuloServiceTest(ArticuloService articuloService, GeneroService generoService,ArticuloRepository articuloRepository) {
		this.articuloService = articuloService;
		this.generoService = generoService;
		this.articuloRepository = articuloRepository;
	}
	
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
		assertThat(a).isEqualTo(articulo);
	}

	@Test
	void testArticulosEnVentaByProvider() {
		List<Articulo> articulos1 = this.articuloService.articulosEnVentaByProvider(ARTICULO_ID);
		assertThat(articulos1.size()).isEqualTo(5);
		
		List<Articulo> articulos2 = this.articuloService.articulosEnVentaByProvider(10);
		assertThat(articulos2).isEmpty();
	}
	
	@Test
	void testEliminarArticulo() {
		Articulo articulo = articuloService.findArticuloById(ARTICULO_ID);
		assertThat(articulo.getStock()).isPositive();
		
		articuloService.eliminarArticulo(ARTICULO_ID);
		assertThat(articulo.getStock()).isZero();
	}
	
	@Test
	void testArticulosDisponibles() {
		List<Articulo> articulosDisponibles = articuloService.articulosDisponibles();
		
		assertThat(articulosDisponibles.size()).isPositive();
	}
	
	@Test
	void testArticulosOfertados() {
		List<Articulo> articulosOfertados = articuloService.articulosOfertados();
		
		assertThat(articulosOfertados.size()).isPositive();
		
		assertThat(articulosOfertados.get(0).getOferta().isDisponibilidad()).isTrue();
	}
	
	@Test
	void testOfertasRandom() {
		List<Articulo> ofertasRandom = articuloService.ofertasRandomAcotada();
		
		assertThat(ofertasRandom.size()).isBetween(0, 5);
	
		assertThat(ofertasRandom.get(0).getOferta().isDisponibilidad()).isTrue();
	}
	
	@Test
	void testAcotarOfertas() {
		List<Articulo> articulos = articuloService.articulosDisponibles();
		for(int i = 0; i < 6; i++) 
			articulos.get(i).getOferta().setDisponibilidad(true);
		
		List<Articulo> ofertasRandom = articuloService.ofertasRandomAcotada();
		
		assertThat(ofertasRandom.size()).isEqualTo(5);
	
	}
	
	@Test
	void testArticulosRelacionados() {
		Articulo articulo = articuloService.findArticuloById(ARTICULO_ID);
		List<Articulo> relacionados = articuloService.articulosRelacionados(articulo);
		assertThat(relacionados.size()).isPositive();
		
		assertThat(relacionados.get(0).getGeneros()).containsAnyElementsOf(articulo.getGeneros());
	}
	
	@Test
	void testAcotarRelacionados() {
		Articulo articulo = articuloService.findArticuloById(ARTICULO_ID);
		articulo.setGeneros(new HashSet<>());
		List<Articulo> relacionados = articuloService.articulosRelacionados(articulo);
		assertThat(relacionados.size()).isEqualTo(6);
		
		assertThat(relacionados.get(0).getGeneros()).containsAnyElementsOf(articulo.getGeneros());
	}
	
	@Test
	void testBusquedaCadena() {
		Articulo articulo = new Articulo();
		String cadena = "MSI";
		articulo.setModelo(cadena);
		
		List<Articulo> busqueda = articuloService.busqueda(articulo);
		
		assertThat(busqueda.size()).isPositive();
		
		assertThat(busqueda.get(0).getMarca()).contains(cadena);
	}
	
	public Set<Genero> obtieneGeneros() {
		Genero gen1 = generoService.findGeneroById(1);
		Genero gen2 = generoService.findGeneroById(4);
		Set<Genero> result = new HashSet<>();
		result.add(gen1);
		result.add(gen2);
		return result;
	}
	
	@Test
	void testBusquedaGeneros() {
		Articulo articulo = new Articulo();
		Set<Genero> generos = obtieneGeneros();
		
		articulo.setModelo("");
		articulo.setGeneros(generos);
		
		List<Articulo> busqueda = articuloService.busqueda(articulo);
		
		assertThat(busqueda.size()).isPositive();
		
		assertThat(busqueda.get(0).getGeneros()).containsAnyElementsOf(generos);
	}
	
	@Test
	void testBusquedaCadenaYGeneros() {
		Articulo articulo = new Articulo();
		Set<Genero> generos = obtieneGeneros();
		String cadena = "Sony";
		articulo.setModelo(cadena);
		articulo.setGeneros(generos);
		
		List<Articulo> busqueda = articuloService.busqueda(articulo);
		
		assertThat(busqueda.size()).isPositive();
		
		assertThat(busqueda.get(0).getGeneros()).containsAnyElementsOf(generos);
		
		assertThat(busqueda.get(0).getMarca()).contains(cadena);
	}
	
	@Test
	void testMensajeCadena() {
		Articulo articulo = new Articulo();
		
		String cadena = "Sony";
		articulo.setModelo(cadena);
		
		String mensaje = articuloService.mensajeDeBusqueda(articulo);
		
		assertThat(mensaje).contains("Sony");
		
	}
	
	@Test
	void testMensajeGeneros() {
		Articulo articulo = new Articulo();
		
		Set<Genero> generos = obtieneGeneros();
		articulo.setModelo("");
		articulo.setGeneros(generos);
		
		String mensaje = articuloService.mensajeDeBusqueda(articulo);
		
		assertThat(mensaje).contains("con los géneros seleccionados");
		
	}
	
	@Test
	void testMensajeCadenaGeneros() {
		Articulo articulo = new Articulo();
		
		Set<Genero> generos = obtieneGeneros();
		String cadena = "Sony";
		articulo.setModelo(cadena);
		articulo.setGeneros(generos);
		
		String mensaje = articuloService.mensajeDeBusqueda(articulo);
		
		assertThat(mensaje).contains("con los géneros seleccionados").contains(cadena);
		
	}
	
	@Test
	void testArticuloDisponibleRepository() {
		
		assertThat(articuloRepository.articulosDisponibles()).hasSize(10);
	}
	
	@Test
	void testArticuloEnOfertaRepository() {
		
		assertThat(articuloRepository.articulosOfert()).hasSize(2);
	}
	
	@Test
	void testArticuloEnVentaIdRepository() {
		
		assertThat(articuloRepository.articulosEnVentaPorId(VENDEDOR_ID)).hasSize(5);
	}
	
	@Test
	void testArticuloPorNombreRepository() {
		
		assertThat(articuloRepository.articulosPorNombre("msi")).hasSize(1);

	}
	
	
	@Test
	void testArticuloPorGeneroRepository() {
		List<Integer> generoId = new ArrayList<>();
		generoId.add(1);generoId.add(2);generoId.add(3);
		assertThat(articuloRepository.articulosPorGenero(new ArrayList<>(15))).isEmpty();
		assertThat(articuloRepository.articulosPorGenero(generoId)).hasSize(4);
	}
	
	@Test
	void testArticuloPorGeneroNombreRepository() {
		List<Integer> generoId = new ArrayList<>();
		generoId.add(1);generoId.add(2);generoId.add(3);
		assertThat(articuloRepository.articulosPorGeneroNombre(new ArrayList<>(), "kjhlh")).isEmpty();
		assertThat(articuloRepository.articulosPorGeneroNombre(generoId, "msi")).hasSize(1);
	}
	
}
