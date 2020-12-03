package org.springframework.samples.dpc.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.service.BloqueoService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BloqueoServiceTest {

	@Autowired
	private BloqueoService bloqueoService;
	
	@Test
	void testEdit() {
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		bloqueo.setBloqueado(false);
		assertTrue(bloqueo.getDescripcion().isEmpty());
		
		Bloqueo bloqueo2 = this.bloqueoService.findBlockById(2);
		bloqueo2.setBloqueado(true);
		bloqueo2.setDescripcion("Bloqueado por mal comportamiento.");
		assertFalse(bloqueo2.getDescripcion().isEmpty());
		
	}
	
}
