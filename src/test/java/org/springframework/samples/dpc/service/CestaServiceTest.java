package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.repository.CestaRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CestaServiceTest {

	private CestaService cestaService;
	private CestaRepository cestaRepository;
	
	@Autowired
	public CestaServiceTest(CestaService cestaService, CestaRepository cestaRepository) {
		this.cestaService = cestaService;
		this.cestaRepository = cestaRepository;
	}

	@Test
	@DisplayName("Test Crear cesta")
	void testCrear() {
		Cesta c = new Cesta();
		List<LineaCesta> lineas = this.cestaService.findCestaById(1).getLineas();
		c.setId(4);
		c.setLineas(lineas);
		c.setVersion(1);

		assertThat(cestaRepository.count()).isEqualTo(3);		
		this.cestaService.crearCesta(c);
		assertThat(cestaRepository.count()).isEqualTo(4);
	}
}
