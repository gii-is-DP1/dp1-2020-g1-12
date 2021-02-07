package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ComentarioServiceTest {
	
	private final Integer TEST_COMENTARIO_ID = 1;
	private final Integer TEST_ARTICULO_ID = 1;

	private final ComentarioService comentarioService;
	
	@Autowired
	public ComentarioServiceTest(ComentarioService comentarioService) {
		this.comentarioService = comentarioService;
	}

	@Test
	@DisplayName("Test Obtener comentarios de un artículo")
	void testObtenerComentariosArticulo() {
		List<Comentario> comentarios = this.comentarioService.getComentariosDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(comentarios.size()).isEqualTo(4);
		assertThat(comentarios.get(0).getId()).isEqualTo(1);
	}
	
	@Test 
	@DisplayName("Test Obtener valoración media de un artículo")
	void testObtenerValoracionMedia() {
		Double valoracion = this.comentarioService.getValoracionDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(valoracion).isEqualTo((double) 3);
	}
	
	@Test
	@DisplayName("Test Eliminar comentario de un artículo")
	void testEliminarComentario() {
		Comentario comentario = this.comentarioService.findCommentById(TEST_COMENTARIO_ID);
		this.comentarioService.eliminarComentario(comentario);
		List<Comentario> comentarios = this.comentarioService.getComentariosDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(comentarios.size()).isEqualTo(3);
	}
}
