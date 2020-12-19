package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.model.Moderador;
import org.springframework.samples.dpc.service.exceptions.ComentarioProhibidoException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ComentarioServiceTest {
	
	private final Integer TEST_COMENTARIO_ID = 1;
	private final Integer TEST_CLIENTE_ID = 1;
	private final Integer TEST_VENDEDOR_ID = 1;
	private final Integer TEST_ARTICULO_ID = 1;
	private final Integer TEST_MODERADOR_ID = 1;

	private final ComentarioService comentarioService;
	private final ArticuloService articuloService;
	private final UserService userService;
	private final ModeradorService moderadorService;
	private final VendedorService vendedorService;
	private final ClienteService clienteService;
	private Comentario comentario = new Comentario();
	
	@Autowired
	public ComentarioServiceTest(ComentarioService comentarioService, ArticuloService articuloService, 
			UserService userService, ModeradorService moderadorService, VendedorService vendedorService,
			ClienteService clienteService) {
		this.comentarioService = comentarioService;
		this.articuloService = articuloService;
		this.userService = userService;
		this.moderadorService = moderadorService;
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
	
	}
	
	public void inicializa() {
		Articulo articulo = this.articuloService.findArticuloById(TEST_ARTICULO_ID);
		this.comentario.setArticulo(articulo);
		this.comentario.setId(TEST_COMENTARIO_ID);
		this.comentario.setDescripcion("ASDFGHJKLÑQWERTY");
		this.comentario.setValoracion(3);  
	}
	
//	@Test
//	void testGuardarComentarioModerador() throws ComentarioProhibidoException{
//		Comentario comentario = this.comentario;
//		Moderador moderador = this.moderadorService.findModeradorById(TEST_MODERADOR_ID);
//		comentario.setModerador(moderador);
//		this.comentarioService.guardarComentario(comentario, TEST_ARTICULO_ID);
//		assertThat(comentario.getValoracion()).isZero();
//	}
	
	@Test 
	void testObtenerComentariosArticulo() {
		List<Comentario> comentarios = this.comentarioService.getComentariosDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(comentarios.size()).isEqualTo(4);
		assertThat(comentarios.get(0).getId()).isEqualTo(1);
	}
	
	@Test 
	void testObtenerValoracionMedia() {
		Double valoracion = this.comentarioService.getValoracionDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(valoracion).isEqualTo((double) 13/3);
	}
	
//	@Test 
//	void testPuedeComentar() {
//		
//	}
	
	@Test
	void testEliminarComentario() {
		Comentario comentario = this.comentarioService.findCommentById(TEST_COMENTARIO_ID);
		this.comentarioService.eliminarComentario(comentario);
		List<Comentario> comentarios = this.comentarioService.getComentariosDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(comentarios.size()).isEqualTo(3);
	}
	
	
}
