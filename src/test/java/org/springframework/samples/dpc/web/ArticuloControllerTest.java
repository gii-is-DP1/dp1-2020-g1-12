package org.springframework.samples.dpc.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.service.ArticuloService;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.ComentarioService;
import org.springframework.samples.dpc.service.GeneroService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ArticuloController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ArticuloControllerTest {
	
	private static final int TEST_ARTICULO_ID = 1;

	@MockBean 
	private ArticuloService articuloService;
	
	@MockBean 
	private VendedorService vendedorService;
	
	@MockBean 
	private ComentarioService comentarioService;

	@MockBean 
	private CestaService cestaService;
	
	@MockBean 
	private GeneroService generoService;
	
	@MockBean 
	private ClienteService clienteService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Articulo articulo;
	
	@BeforeEach
	void setup() {
		
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
		List<Articulo> l = new ArrayList<>();
		l.add(articulo);
		given(this.articuloService.ofertasRandomAcotada()).willReturn(l);
		given(this.articuloService.articulosDisponibles(0, 10, "-id")).
		willReturn(new PageImpl<>(l, PageRequest.of(0, 10, Sort.by(Order.desc("id"))), 10));
		given(this.articuloService.obtenerFiltros(0, 10, "-id", "articulo")).willReturn(PageRequest.of(0, 10));
		given(this.articuloService.findArticuloById(TEST_ARTICULO_ID)).willReturn(articulo);
	}

	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Listado de artículos")
    void testListadoArticulo() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(model().attributeExists("articulos","generos","ofertas","query"))
		.andExpect(status().is2xxSuccessful()).andExpect(view().name("articulos/principal"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Obtener detalles artículo")
    void testDetallesArticulo() throws Exception {
		mockMvc.perform(get("/articulos/"+TEST_ARTICULO_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("query","generos","articulo","valoracion","puedeComentar","relacionados","comentarios")).andExpect(view().name("articulos/detalles"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Búsqueda de artículos")
    void testBusqueda() throws Exception {
		mockMvc.perform(post("/busqueda").param("modelo", "msi").with(csrf()))
			.andExpect(status().isOk()).andExpect(model().attributeExists("query","generos")).andExpect(status().is2xxSuccessful())
			.andExpect(view().name("/articulos/principal"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Búsqueda vacía")
    void testBusquedaIf() throws Exception {
		mockMvc.perform(post("/busqueda").param("modelo","").with(csrf()))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    @DisplayName("Test Obtener artículos en oferta")
    void testArtículosEnOferta() throws Exception {
		mockMvc.perform(get("/ofertas")).andExpect(status().isOk()).andExpect(model().attributeExists("ofertas"))
		.andExpect(view().name("/articulos/ofertas"));
	}
}
