package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Oferta;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OfertaServiceTest {
	
	@Autowired
	private OfertaService ofertaService;
	
	@Test
	void testSave() {
		Oferta o = new Oferta();
		o.setPorcentaje(5);
		o.setDisponibilidad(true);
		this.ofertaService.guardarOferta(o);
		Integer id = o.getId();
		Oferta oferta = this.ofertaService.findOfertById(id);
		assertEquals(o, oferta);
	}
	
	@Test
	void testEdit() {
		Oferta oferta = this.ofertaService.findOfertById(1);
		oferta.setPorcentaje(10);
		this.ofertaService.editar(oferta, oferta.getId(), true);
		assertThat(oferta.getPorcentaje()).isEqualTo(10);
	}
	
}
