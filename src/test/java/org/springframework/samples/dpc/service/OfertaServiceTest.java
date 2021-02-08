package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class OfertaServiceTest {
	
	@Autowired
	private OfertaService ofertaService;
	
	@Test
	@DisplayName("Test Crear una oferta")
	void testSave() {
		Oferta o = new Oferta();
		o.setPorcentaje(5);
		o.setDisponibilidad(true);
		this.ofertaService.guardarOferta(o);
		Integer id = o.getId();
		Oferta oferta = this.ofertaService.findOfertById(id);
		assertEquals(o, oferta);
	}
	
	@ParameterizedTest
	@DisplayName("Test Editar una oferta")
	@ValueSource(ints = {10, 30, 40, 60, 70})
	void testEdit(Integer porcentaje) {
		Oferta oferta = this.ofertaService.findOfertById(1);
		oferta.setPorcentaje(porcentaje);
		this.ofertaService.editar(oferta, oferta.getId(), true);
		assertThat(oferta.getPorcentaje()).isEqualTo(porcentaje);
	}
}
