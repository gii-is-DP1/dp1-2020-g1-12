package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ComentarioController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ComentarioControllerTest {

	private static final int TEST_COMENTARIO_ID = 1;
	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_ARTICULO_ID = 1;
	
	@MockBean
	private ComentarioService comentarioService;
	
	@MockBean
	private ArticuloService articuloService;
	
	@MockBean
	private VendedorService vendedorService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Comentario comentario;
	
	private Articulo articulo;
	
	private Vendedor vendedor;
	
	@BeforeEach
	void setup() {
		comentario = new Comentario();
		Vendedor vend = new Vendedor();
		vend.setId(TEST_VENDEDOR_ID);
		vend.setDni("56789876");
		vend.setNombre("Quique");
		vend.setApellido("Salazar");
		vend.setDireccion("C/Calle");
		vend.setTelefono("615067389");
		vend.setEmail("mail@mail.com");
		Bloqueo b = new Bloqueo();
		b.setId(1);
		b.setBloqueado(false);
		b.setDescripcion("");
		vend.setBloqueo(b);
		comentario.setVendedor(vend);
		articulo = new Articulo();
		articulo.setId(TEST_ARTICULO_ID);
		articulo.setMarca("MSI");
		articulo.setModelo("Prestige Evo A11M-003ES");
		articulo.setUrlImagen("https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png");
		articulo.setGastoEnvio(5.0);
		articulo.setPrecio(988.99);
		articulo.setStock(10);
		articulo.setTiempoEntrega(8);
		articulo.setTipo(Tipo.Nuevo);
		Oferta oferta = new Oferta();
		oferta.setId(1);
		oferta.setDisponibilidad(false);
		oferta.setPorcentaje(5);
		articulo.setOferta(oferta);	
		comentario.setArticulo(articulo);
		comentario.setDescripcion("asdfghjklñpoiuytr");
		comentario.setValoracion(4);
		comentario.setId(TEST_COMENTARIO_ID);
		
		given(this.comentarioService.findCommentById(TEST_COMENTARIO_ID)).willReturn(comentario);
		given(this.articuloService.findArticuloById(TEST_ARTICULO_ID)).willReturn(articulo);
		given(this.vendedorService.findSellerById(TEST_VENDEDOR_ID)).willReturn(vendedor);
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Formulario crear comentario en artículo")
    void testCrearComentario() throws Exception {
		mockMvc.perform(get("/comentario/articulo/" + TEST_ARTICULO_ID)).andExpect(status().isOk())
			.andExpect(model().attributeExists("comentario" , "articulo"))
			.andExpect(view().name("articulos/editarComentario"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear comentario en artículo (con error de valoración)")
    void testProcesoComentarConErrores() throws Exception {
		mockMvc.perform(post("/comentario/articulo/" + TEST_ARTICULO_ID).param("descripcion", "ASDFGHJKLÑQWUYEI")
				.param("valoracion", "4.5").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("articulos/editarComentario"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Crear comentario en artículo")
    void testProcesoComentar() throws Exception {
		mockMvc.perform(post("/comentario/articulo/" + TEST_ARTICULO_ID).param("descripcion", "ASDFGHJKLÑQWUYEI")
				.param("valoracion", "4").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Eliminar comentario en artículo")
    void testEliminarComentario() throws Exception {
		mockMvc.perform(get("/comentario/eliminar/" + TEST_COMENTARIO_ID + "/articulo/" + TEST_ARTICULO_ID))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Editar comentario")
    void testEditarComentario() throws Exception {
		mockMvc.perform(get("/comentario/editar/" + TEST_COMENTARIO_ID + "/articulo/" + TEST_ARTICULO_ID))
		.andExpect(status().isOk()).andExpect(model().attributeExists("comentario" , "articulo"))
			.andExpect(view().name("articulos/editarComentario"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test de error Proceso editar comentario (valoración mayor)")
    void testProcesoEditarComentarioConErrores() throws Exception {
		mockMvc.perform(post("/comentario/editar/" + TEST_COMENTARIO_ID + "/articulo/" + TEST_ARTICULO_ID)
				.param("descripcion", "ASDFGHJKLÑQWUYEI")
				.param("valoracion", "7").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("articulos/editarComentario"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Proceso editar comentario")
    void testProcesoEditarComentario() throws Exception {
		mockMvc.perform(post("/comentario/editar/" + TEST_COMENTARIO_ID + "/articulo/" + TEST_ARTICULO_ID)
				.param("descripcion", "ASDFGHJKLÑQWUYEI").param("valoracion", "4").with(csrf()))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/articulos/{articuloId}"));
	}
}
