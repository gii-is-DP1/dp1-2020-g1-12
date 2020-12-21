package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.service.exceptions.PrecioMenorAlEnvioException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class TarjetaServiceTest {
	
	private static final int TARJETA_ID = 1;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	@Test
	void testBuscarTarjetaId() {
		TarjetaCredito tarjeta = tarjetaService.findTarjetaById(TARJETA_ID);
		assertThat(tarjeta.getTitular()).isEqualTo("Juan Fernández Tirado");
		assertThat(tarjeta.getNumero()).isEqualTo("1234567899876087");
		assertThat(tarjeta.getCvv()).isEqualTo("442");
		assertThat(tarjeta.getMesCaducidad()).isEqualTo("03");
		assertThat(tarjeta.getAnyoCaducidad()).isEqualTo("22");
	}
	
	public TarjetaCredito arrange() throws Exception { //Todavía hay que crear la validación

		TarjetaCredito tarjeta = new TarjetaCredito();
		tarjeta.setId(2);
		tarjeta.setNumero("1234567899876087");
		tarjeta.setTitular("Juan Fernández Tirado");
		tarjeta.setCvv("442");
		tarjeta.setAnyoCaducidad("22");
		tarjeta.setMesCaducidad("03");
        return tarjeta;

	}



}
