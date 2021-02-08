package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertSame;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Genero;
import org.springframework.samples.dpc.repository.GeneroRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class GeneroServiceTest {
	
	private final Integer ARTICULO_ID = 1;
	private final Integer GENERO_ID2 = 1;
	private final Integer GENERO_ID = 2;
	
	private ArticuloService articuloService;
	private GeneroRepository generoRepository;
	private GeneroService generoService;
	
	@Autowired
	public GeneroServiceTest(ArticuloService articuloService, GeneroService generoService,GeneroRepository generoRepository) {
		this.articuloService = articuloService;
		this.generoService = generoService;
		this.generoRepository = generoRepository;
	}
	
	
	@Test
    @DisplayName("Test Añadir género")
	void testAnyadirGenero() {
		Articulo art = articuloService.findArticuloById(ARTICULO_ID);
		Genero genero = generoService.findGeneroById(GENERO_ID2);
		assertThat(art.getGeneros()).doesNotContain(genero);
		this.generoService.anyadirGenero(ARTICULO_ID, genero);
		assertThat(art.getGeneros()).contains(genero);

	}
	
	@Test
    @DisplayName("Test de error Añadir género (ID nula)")
	void testAnyadirGeneroNull() {
		Articulo art = articuloService.findArticuloById(ARTICULO_ID);
		Genero genero = new Genero();
		genero.setId(null);
		Set<Genero> generosAntes = art.getGeneros();
		this.generoService.anyadirGenero(ARTICULO_ID, genero);
		Set<Genero> generosAhora = art.getGeneros();
		assertSame(generosAntes, generosAhora);
	}
	
	@Test
    @DisplayName("Test Eliminar género")
	void testEliminarGenero() {
		Articulo art = articuloService.findArticuloById(ARTICULO_ID);
		Genero genero = generoService.findGeneroById(GENERO_ID);
		assertThat(art.getGeneros()).contains(genero);
		generoService.eliminarGenero(ARTICULO_ID, GENERO_ID);
		assertThat(art.getGeneros()).doesNotContain(genero);
		
	}

	@Test
    @DisplayName("Test Obtener géneros restantes")
	void testGenerosRestantes() {
		Set<Genero> todosGeneros = ((Collection<Genero>) generoService.findAllGeneros()).stream().collect(Collectors.toSet());
		Set<Genero> generosArticulo = articuloService.findArticuloById(ARTICULO_ID).getGeneros();
		List<Genero> generosRestantes = generoService.generosRestantes(ARTICULO_ID);
		assertThat(generosRestantes.size() + generosArticulo.size()).isEqualTo(todosGeneros.size());
		assertThat(generosRestantes).doesNotContainAnyElementsOf(generosArticulo);
	}
	
	@Test
    @DisplayName("Test Obtener géneros restantes (repositorio)")
	void testGenerosRestantesRepository() {
		Set<Genero> generosActuales = ((Collection<Genero>) generoService.findAllGeneros()).stream().collect(Collectors.toSet());
		Genero g = new Genero();
		g.setId(1);
		g.setNombre("Smartphone");
		g.setVersion(1);
		Set<Genero> gLista = new HashSet<>();
		gLista.add(g);

		assertThat(generoRepository.generosRestantes(generosActuales)).isEmpty();
		assertThat(generoRepository.generosRestantes(gLista)).hasSize(generosActuales.size()-1);
	}
	
}
