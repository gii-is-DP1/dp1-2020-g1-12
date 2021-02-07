package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ArticuloServiceTest {

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
	@DisplayName("Guardar articulo")
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
	@DisplayName("Test de Listado de articulos en venta de un proveedor")
	void testArticulosEnVentaByProvider() {
		List<Articulo> articulos1 = this.articuloService.articulosEnVentaByProvider(ARTICULO_ID, 0 , 100, "id").getContent();
		assertThat(articulos1.size()).isEqualTo(5);
		
		List<Articulo> articulos2 = this.articuloService.articulosEnVentaByProvider(10, 0 , 100, "id").getContent();
		assertThat(articulos2).isEmpty();
	}
	
	@Test
	@DisplayName("Test Eliminar un articulo de un proveedor")
	void testEliminarArticulo() {
		Articulo articulo = articuloService.findArticuloById(ARTICULO_ID);
		assertThat(articulo.getStock()).isPositive();
		
		articuloService.eliminarArticulo(ARTICULO_ID);
		assertThat(articulo.getStock()).isZero();
	}
	
	@Test
	@DisplayName("Test Listado de artículos disponibles")
	void testArticulosDisponibles() {
		List<Articulo> articulosDisponibles = articuloService.articulosDisponibles(0, 
				(int) articuloRepository.count(), "-id").getContent();
		
		assertThat(articulosDisponibles.size()).isPositive();
	}
	
	@Test
	@DisplayName("Test Artículos en oferta")
	void testArticulosOfertados() {
		List<Articulo> articulosOfertados = articuloService.articulosOfertados();
		
		assertThat(articulosOfertados.size()).isPositive();
		
		assertThat(articulosOfertados.get(0).getOferta().isDisponibilidad()).isTrue();
	}
	
	@Test
	@DisplayName("Test Ofertas aleatorias")
	void testOfertasRandom() {
		List<Articulo> ofertasRandom = articuloService.ofertasRandomAcotada();
		
		assertThat(ofertasRandom.size()).isBetween(0, 5);
	
		assertThat(ofertasRandom.get(0).getOferta().isDisponibilidad()).isTrue();
	}
	
	@Test
	@DisplayName("Test Número de artículos en oferta")
	void testAcotarOfertas() {
		List<Articulo> articulos = articuloService.articulosDisponibles(0, 
				(int) articuloRepository.count(), "-id").getContent();
		for(int i = 0; i < 6; i++) 
			articulos.get(i).getOferta().setDisponibilidad(true);
		
		List<Articulo> ofertasRandom = articuloService.ofertasRandomAcotada();
		
		assertThat(ofertasRandom.size()).isEqualTo(5);
	
	}
	
	@Test
	@DisplayName("Test Artículos relacionados")
	void testArticulosRelacionados() {
		Articulo articulo = articuloService.findArticuloById(ARTICULO_ID);
		List<Articulo> relacionados = articuloService.articulosRelacionados(articulo);
		assertThat(relacionados.size()).isPositive();
		
		assertThat(relacionados.get(0).getGeneros()).containsAnyElementsOf(articulo.getGeneros());
	}
	
	@Test
	@DisplayName("Test Búsqueda dada una cadena")
	void testBusquedaCadena() {
		Articulo articulo = new Articulo();
		String cadena = "MSI";
		articulo.setModelo(cadena);
		List<Articulo> busqueda = articuloService.busqueda(articulo, 0, 
				(int) articuloRepository.count(), "-id").getContent();
		
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
	@DisplayName("Test Búsqueda por género")
	void testBusquedaGeneros() {
		Articulo articulo = new Articulo();
		Set<Genero> generos = obtieneGeneros();
		
		articulo.setModelo("");
		articulo.setGeneros(generos);
		
		List<Articulo> busqueda = articuloService.busqueda(articulo, 0, 
				(int) articuloRepository.count(), "-id").getContent();
		
		assertThat(busqueda.size()).isPositive();
		
		assertThat(busqueda.get(0).getGeneros()).containsAnyElementsOf(generos);
	}
	
	@Test
	@DisplayName("Test Búsqueda por género y cadena")
	void testBusquedaCadenaYGeneros() {
		Articulo articulo = new Articulo();
		Set<Genero> generos = obtieneGeneros();
		String cadena = "Sony";
		articulo.setModelo(cadena);
		articulo.setGeneros(generos);
		
		List<Articulo> busqueda = articuloService.busqueda(articulo, 0, 
				(int) articuloRepository.count(), "-id").getContent();
		
		assertThat(busqueda.size()).isPositive();
		
		assertThat(busqueda.get(0).getGeneros()).containsAnyElementsOf(generos);
		
		assertThat(busqueda.get(0).getMarca()).contains(cadena);
	}
	
	@Test
	@DisplayName("Test Cadena de Búsqueda")
	void testMensajeCadena() {
		Articulo articulo = new Articulo();
		
		String cadena = "Sony";
		articulo.setModelo(cadena);
		
		String mensaje = articuloService.mensajeDeBusqueda(articulo);
		
		assertThat(mensaje).contains("Sony");
		
	}
	
	@Test
	@DisplayName("Test Cadena búsqueda")
	void testMensajeGeneros() {
		Articulo articulo = new Articulo();
		
		Set<Genero> generos = obtieneGeneros();
		articulo.setModelo("");
		articulo.setGeneros(generos);
		
		String mensaje = articuloService.mensajeDeBusqueda(articulo);
		
		assertThat(mensaje).contains("con los géneros seleccionados");
		
	}
	
	@Test
	@DisplayName("Test Generos buscados")
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
	@DisplayName("Test Artículos de un vendedor")
	void testArticulosDeVendedor() {
		List<Articulo> articulos = articuloService.articulosByProvider(1);
		assertThat(articulos.size()).isNotZero();
	}
	
	@Test
	@DisplayName("Test Articulos disponibles (Repositorio)")
	void testArticuloDisponibleRepository() {
		Pageable pageable = PageRequest.of(0, (int) articuloRepository.count(), 
				Sort.by(new Order(Sort.Direction.DESC, "id")));
		assertThat(articuloRepository.articulosDisponibles(pageable)).hasSize(11);
	}
	
	@Test
	@DisplayName("Test Articulos en oferta (Repositorio)")
	void testArticuloEnOfertaRepository() {
		
		assertThat(articuloRepository.articulosOfert()).hasSize(2);
	}
	
	@Test
	@DisplayName("Test Articulo en venta de un proveedor (Repositorio)")
	void testArticuloEnVentaIdRepository() {
		Pageable pageable = PageRequest.of(0, 100, Sort.by(new Order(Sort.Direction.ASC, "id")));
		assertThat(articuloRepository.articulosEnVentaPorId(VENDEDOR_ID, pageable)).hasSize(5);
	}
	
	@Test
	@DisplayName("Test Búsqueda por cadena(Repositorio)")
	void testArticuloPorNombreRepository() {
		Pageable pageable = PageRequest.of(0, (int) articuloRepository.count(), 
				Sort.by(new Order(Sort.Direction.DESC, "id")));
		assertThat(articuloRepository.articulosPorNombre("msi", pageable)).hasSize(1);

	}
	
	
	@Test
	@DisplayName("Test Búsqueda por generos(Repositorio)")
	void testArticuloPorGeneroRepository() {
		Pageable pageable = PageRequest.of(0, (int) articuloRepository.count(), 
				Sort.by(new Order(Sort.Direction.DESC, "id")));
		List<Integer> generoId = new ArrayList<>();
		generoId.add(1);generoId.add(2);generoId.add(3);
		assertThat(articuloRepository.articulosPorGenero(new ArrayList<>(15), pageable)).isEmpty();
		assertThat(articuloRepository.articulosPorGenero(generoId, pageable)).hasSize(5);
	}
	
	@Test
	@DisplayName("Test Búsqueda por generos y cadenas(Repositorio)")
	void testArticuloPorGeneroNombreRepository() {
		Pageable pageable = PageRequest.of(0, (int) articuloRepository.count(), 
				Sort.by(new Order(Sort.Direction.DESC, "id")));
		List<Integer> generoId = new ArrayList<>();
		generoId.add(1);generoId.add(2);generoId.add(3);
		assertThat(articuloRepository.articulosPorGeneroNombre(new ArrayList<>(), "kjhlh", pageable)).isEmpty();
		assertThat(articuloRepository.articulosPorGeneroNombre(generoId, "msi", pageable)).hasSize(1);
	}
	
}
