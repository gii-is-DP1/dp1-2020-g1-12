package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.MensajeRepository;
import org.springframework.samples.dpc.service.exceptions.MensajeProhibidoException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MensajeServiceTest {

	private MensajeService mensajeService;
	private MensajeRepository mensajeRepository;	
	private VendedorService vendedorService;
	private ClienteService clienteService;
	
	@Autowired
	public MensajeServiceTest(MensajeService mensajeService, MensajeRepository mensajeRepository, 
			ClienteService clienteService, VendedorService vendedorService) {
		this.mensajeService = mensajeService;
		this.mensajeRepository = mensajeRepository;
		this.clienteService = clienteService;
		this.vendedorService = vendedorService;
	}

	@Test
	@DisplayName("Test Obtener mensages no leidos")
	void testGetMensajesNoLeidosCliente() throws MensajeProhibidoException {
		Cliente cliente = this.clienteService.findClientById(1);
		Vendedor vendedor = this.vendedorService.findSellerById(1);
		
		assertThat(mensajeService.getMensajesNoLeidosCliente(cliente, vendedor)).isEqualTo(2);
		Mensaje mensaje = new Mensaje();
		mensaje.setCliente(cliente);
		mensaje.setVendedor(vendedor);
		mensaje.setDestinatario(cliente.getDni());
		mensaje.setEmisor(vendedor.getDni());
		mensaje.setFechaEnvio(LocalDateTime.now());
		mensaje.setVersion(1);
		mensaje.setTexto("Texto");
		mensaje.setLectura("01");
		this.mensajeRepository.save(mensaje);
		assertThat(mensajeService.getMensajesNoLeidosCliente(cliente, vendedor)).isEqualTo(3);
	}

	@Test
	@DisplayName("Test Confirmar la lectura de un chat")
	void testConfirmarLectura() throws MensajeProhibidoException {
		Cliente cliente = this.clienteService.findClientById(1);
		Vendedor vendedor = this.vendedorService.findSellerById(1);
		
		assertThat(mensajeService.getMensajesNoLeidosCliente(cliente, vendedor)).isEqualTo(2);
		this.mensajeService.confimarLectura(cliente, vendedor);
		assertThat(mensajeService.getMensajesNoLeidosCliente(cliente, vendedor)).isEqualTo(0);
	}
}
