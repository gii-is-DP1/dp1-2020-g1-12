package org.springframework.samples.dpc.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.service.exceptions.BloquearSinDescripcionException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class BloqueoServiceTest {

	private BloqueoService bloqueoService;

	@Autowired
	public BloqueoServiceTest(BloqueoService bloqueoService) {
		this.bloqueoService = bloqueoService;
	}
	
	@Test
	void testEdit() throws BloquearSinDescripcionException{
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		bloqueo.setDescripcion("HOLA ME LLAMO PEPE");
		this.bloqueoService.editar(bloqueo, bloqueo.getId(), true);
		assertTrue(bloqueo.isBloqueado());	
	}
	
	@Test
	void testEditConExcepcion() throws BloquearSinDescripcionException{
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		bloqueo.setDescripcion("HOLA");
		assertThrows(BloquearSinDescripcionException.class,
				() -> this.bloqueoService.editar(bloqueo, bloqueo.getId(), true));	
	}
	
	@Test
	void testEditSinBloqueo() throws BloquearSinDescripcionException{
		Bloqueo bloqueo = this.bloqueoService.findBlockById(1);
		this.bloqueoService.editar(bloqueo, bloqueo.getId(), false);
		assertFalse(bloqueo.isBloqueado());	
	}
	
	@Test
	void testSave() throws BloquearSinDescripcionException{
		Bloqueo bloqueo = new Bloqueo();
		bloqueo.setBloqueado(true);
		bloqueo.setDescripcion("HOLA ME LLAMO PEPE");
		this.bloqueoService.guardar(bloqueo);
		assertTrue(bloqueo.isBloqueado());	
	}
}
