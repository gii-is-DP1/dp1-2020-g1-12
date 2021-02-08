package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.repository.LineaCestaRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

class LineaCestaServiceTest {
	
	private final Integer ARTICULO_ID = 3;
	private final Integer LINEA_CESTA_ID = 1;
	private final Integer CESTA_ID = 1;
	
	private LineaCestaRepository lineaCestaRepository;
	private LineaCestaService lineaCestaService;
	private CestaService cestaService;
	
	@Autowired	
	public LineaCestaServiceTest(LineaCestaRepository lineaCestaRepository, LineaCestaService lineaCestaService,
			CestaService cestaService) {
		this.lineaCestaRepository = lineaCestaRepository;
		this.lineaCestaService = lineaCestaService;
		this.cestaService = cestaService;
	}
	
	@Test
	@DisplayName("Test Crear una linea de cesta")
	void testCrearLinea() {
		Integer lineas1=1;
		Iterator<LineaCesta> it = lineaCestaRepository.findAll().iterator();
		while(it.hasNext()) {
			it.next();
			lineas1++;
		}
		this.lineaCestaService.crearLinea(ARTICULO_ID, cestaService.findCestaById(CESTA_ID));
		Iterator<LineaCesta> it2 = lineaCestaRepository.findAll().iterator();
		Integer lineas2=1;
		while(it2.hasNext()) {
			it2.next();
			lineas2++;
		}
		assertThat(lineas2).isEqualTo(lineas1+1);
	}
	
	@Test
	@DisplayName("Test Eliminar una linea de cesta")
	void testEliminarLinea() {
		LineaCesta linea = this.lineaCestaService.findLineaById(LINEA_CESTA_ID);
		assertThat(linea).isNotNull();
		lineaCestaService.eliminarLinea(linea);
		LineaCesta linea2 = this.lineaCestaService.findLineaById(LINEA_CESTA_ID);
		assertThat(linea2).isNull();
	}
}
