package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class ClienteServiceTest {
	
	@Autowired
	private ClienteService clienteService;
	
	@Test
	public void testCountWithInititalData() {
		int count=clienteService.clienteCount();
		assertEquals(count,1);
	}
	

}
