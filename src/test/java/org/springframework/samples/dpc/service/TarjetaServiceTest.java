package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

class TarjetaServiceTest {
	
	private static final int TARJETA_ID = 1;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	@Test
	@DisplayName("Test Buscar tarjeta por id")
	void testBuscarTarjetaId() {
		TarjetaCredito tarjeta = tarjetaService.findTarjetaById(TARJETA_ID);
		assertThat(tarjeta.getTitular().split(" ")[0]).isEqualTo("Juan");
		assertThat(tarjeta.getNumero()).isEqualTo("4572240486955232");
		assertThat(tarjeta.getCvv()).isEqualTo("442");
		assertThat(tarjeta.getFechaCaducidad().split("/")[0]).isEqualTo("03");
		assertThat(tarjeta.getFechaCaducidad().split("/")[1]).isEqualTo("22");
	}
}
