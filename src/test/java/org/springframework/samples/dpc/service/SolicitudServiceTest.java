package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.SolicitudService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.samples.dpc.service.exceptions.PrecioMenorAlEnvioException;
import org.springframework.samples.dpc.service.exceptions.SolicitudRechazadaSinRespuestaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SolicitudServiceTest {
	
	private static final int SOLICITUD_ACEPTADA_ID = 1;
	private static final int SOLICITUD_PENDIENTE_ID = 4;
	private static final int VENDEDOR_ID = 1;
	
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private VendedorService vendedorService;

	@Test
	void testListadoDeSolicitudesPendientes() {
		List<Solicitud> pendientes = this.solicitudService.solicitudesPendientes();
		assertThat(pendientes.size()).isEqualTo(1);
	}

	@Test
	void testBuscarSolicitudPorId() {
		Solicitud solicitud = this.solicitudService.detallesSolicitud(SOLICITUD_ACEPTADA_ID).get();
		assertThat(solicitud.getDescripcion()).isEqualTo("Solicitud de venta de MSI Prestige Evo A11M-003ES");
		assertThat(solicitud.getPrecio()).isEqualTo(988.99);
		assertThat(solicitud.getModelo()).isEqualTo("Prestige Evo A11M-003ES");
		assertThat(solicitud.getArticulo().getMarca()).isEqualTo("MSI");
		assertThat(solicitud.getStock()).isGreaterThan(0);
	}
	
	@Test
	@Transactional
	public void testInsertarSolicitud() throws PrecioMenorAlEnvioException {
		List<Solicitud> pendientes = this.solicitudService.solicitudesPendientes();
		int size = pendientes.size();

		Solicitud sol = new Solicitud();
		sol.setDescripcion("Venta de Apple Iphone XS");
		sol.setModelo("Iphone XS");
		sol.setMarca("Apple");
		sol.setPrecio(500.);
		sol.setUrlImagen("No especificada");
		sol.setStock(100);
		sol.setTipo(Tipo.Nuevo);
		sol.setTiempoEntrega(3);
		sol.setGastoEnvio(600.);
        Vendedor vendedor = vendedorService.findSellerById(VENDEDOR_ID);
        
        assertThrows(PrecioMenorAlEnvioException.class ,() -> this.solicitudService.guardar(sol,vendedor)); // Separar
        
        sol.setGastoEnvio(5.);
        this.solicitudService.guardar(sol,vendedor);
		assertThat(sol.getId().longValue()).isNotEqualTo(0);

		pendientes = this.solicitudService.solicitudesPendientes();
		assertThat(pendientes.size()).isEqualTo(size + 1);
	}
	
	@Test
	void testAceptarSolicitud() {
		Solicitud solicitud = solicitudService.detallesSolicitud(SOLICITUD_PENDIENTE_ID).get();
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Pendiente);
		
		this.solicitudService.aceptarSolicitud(SOLICITUD_PENDIENTE_ID);
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Aceptada);
	}
	
	@Test
	void testDenegarSolicitud() throws SolicitudRechazadaSinRespuestaException {
		Solicitud solicitud = solicitudService.detallesSolicitud(SOLICITUD_ACEPTADA_ID).get();
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Aceptada);
		
		assertThrows(SolicitudRechazadaSinRespuestaException.class, 
				() -> this.solicitudService.denegarSolicitud(SOLICITUD_ACEPTADA_ID, ""));
		
		assertThrows(SolicitudRechazadaSinRespuestaException.class, 
				() -> this.solicitudService.denegarSolicitud(SOLICITUD_ACEPTADA_ID, "Prohibido")); // SEPARAR
		
		
		this.solicitudService.denegarSolicitud(SOLICITUD_ACEPTADA_ID, "No está permitida la venta de RPGs");
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Denegada);
		assertThat(solicitud.getRespuesta()).isEqualTo("No está permitida la venta de RPGs");
	}

}
