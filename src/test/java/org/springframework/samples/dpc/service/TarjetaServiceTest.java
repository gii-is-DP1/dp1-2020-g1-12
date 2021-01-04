package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class TarjetaServiceTest {
	
	private static final int TARJETA_ID = 1;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	public TarjetaCredito arrange() throws Exception { //Todavía hay que crear la validación

		TarjetaCredito tarjeta = new TarjetaCredito();
		tarjeta.setId(2);
		tarjeta.setNumero("1234567899876087");
		tarjeta.setTitular("Juan Fernández Tirado");
		tarjeta.setCvv("442");
		tarjeta.setFechaCaducidad("03/22");
        return tarjeta;

	}
	
	@Test
	void testBuscarTarjetaId() {
		TarjetaCredito tarjeta = tarjetaService.findTarjetaById(TARJETA_ID);
		assertThat(tarjeta.getTitular().split(" ")[0]).isEqualTo("Juan");
		assertThat(tarjeta.getNumero()).isEqualTo("1234567899876087");
		assertThat(tarjeta.getCvv()).isEqualTo("442");
		assertThat(tarjeta.getFechaCaducidad().split("/")[0]).isEqualTo("03");
		assertThat(tarjeta.getFechaCaducidad().split("/")[1]).isEqualTo("22");
	}
	
	@Test
	void testAnyadirTarjeta() {
	}
	
	@Test
	void testEliminarTarjeta() {
		TarjetaCredito tarjeta = tarjetaService.findTarjetaById(TARJETA_ID);
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		securityContext.setAuthentication(authentication);
//		tarjetaService.eliminarTarjetaPersona(TARJETA_ID);
	}
	



}
