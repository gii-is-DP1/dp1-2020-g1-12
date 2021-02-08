package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Situacion;
import org.springframework.samples.dpc.model.Solicitud;
import org.springframework.samples.dpc.model.Tipo;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.exceptions.PrecioMenorAlEnvioException;
import org.springframework.samples.dpc.service.exceptions.SolicitudRechazadaSinRespuestaException;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class SolicitudServiceTest {
	
	private static final int SOLICITUD_ACEPTADA_ID = 1;
	private static final int SOLICITUD_PENDIENTE_ID = 11;
	private static final int VENDEDOR_ID = 1;
	
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private VendedorService vendedorService;

	@Test
    @DisplayName("Test Obtener listado de solicitudes pendientes")
	void testListadoDeSolicitudesPendientes() {
		List<Solicitud> pendientes = this.solicitudService.solicitudesPendientes(0, 1000, "-id").getContent();
		assertThat(pendientes.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("Test Buscar solicitud por Id")
	void testBuscarSolicitudPorId() {
		Solicitud solicitud = this.solicitudService.detallesSolicitud(SOLICITUD_ACEPTADA_ID);
		assertThat(solicitud.getPrecio()).isEqualTo(988.99);
		assertThat(solicitud.getModelo()).isEqualTo("Prestige Evo A11M-003ES");
		assertThat(solicitud.getArticulo().getMarca()).isEqualTo("MSI");
		assertThat(solicitud.getStock()).isPositive();
	}
	
	public Solicitud arrange() throws PrecioMenorAlEnvioException {
		Solicitud sol = new Solicitud();
		sol.setDescripcion("Venta de Apple Iphone XS");
		sol.setModelo("Iphone XS");
		sol.setMarca("Apple");
		sol.setPrecio(500.);
		sol.setUrlImagen("https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg");
		sol.setStock(100);
		sol.setTipo(Tipo.Nuevo);
		sol.setTiempoEntrega(3);      
        sol.setGastoEnvio(5.);
        return sol;

	}
	@Test
	@DisplayName("Test Insertar solicitud")
	void testInsertarSolicitud() throws PrecioMenorAlEnvioException {
		List<Solicitud> pendientes = this.solicitudService.solicitudesPendientes(0, 1000, "-id").getContent();
		int size = pendientes.size();

		Solicitud sol = arrange();
		Vendedor vendedor = vendedorService.findSellerById(VENDEDOR_ID);
        this.solicitudService.guardar(sol,vendedor);
		assertThat(sol.getId().longValue()).isNotZero();

		pendientes = this.solicitudService.solicitudesPendientes(0, 1000, "-id").getContent();
		assertThat(pendientes.size()).isEqualTo(size + 1);
	}
	
	@Test
	@DisplayName("Test Insertar solicitud fallida")
	void testInsertarSolicitudFallida() throws PrecioMenorAlEnvioException {
		List<Solicitud> pendientes = this.solicitudService.solicitudesPendientes(0, 1000, "-id").getContent();
		int size = pendientes.size();

		Solicitud sol = arrange();
		sol.setGastoEnvio(600.);
        Vendedor vendedor = vendedorService.findSellerById(VENDEDOR_ID);
        
        assertThrows(PrecioMenorAlEnvioException.class ,() -> this.solicitudService.guardar(sol,vendedor));

		pendientes = this.solicitudService.solicitudesPendientes(0, 1000, "-id").getContent();
		assertThat(pendientes.size()).isEqualTo(size);
	}
	
	@Test
	@DisplayName("Test Aceptar solicitud")
	void testAceptarSolicitud() {
		Solicitud solicitud = solicitudService.detallesSolicitud(SOLICITUD_PENDIENTE_ID);
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Pendiente);
		
		this.solicitudService.aceptarSolicitud(SOLICITUD_PENDIENTE_ID);
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Aceptada);
	}
	
	@Test
	@DisplayName("Test Denegar solicitud")
	void testDenegarSolicitud() throws SolicitudRechazadaSinRespuestaException {
		Solicitud solicitud = solicitudService.detallesSolicitud(SOLICITUD_ACEPTADA_ID);
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Aceptada);
			
		
		this.solicitudService.denegarSolicitud(SOLICITUD_ACEPTADA_ID, "No está permitida la venta de RPGs");
		
		assertThat(solicitud.getSituacion()).isEqualTo(Situacion.Denegada);
		assertThat(solicitud.getRespuesta()).isEqualTo("No está permitida la venta de RPGs");
	}
	
	@ParameterizedTest
	@DisplayName("Test Denegar solicitud fallidos")
	@ValueSource(strings = {"", "Prohibido"})
	void testDenegarSolicitudFallido(String respuesta) throws SolicitudRechazadaSinRespuestaException {
		
		assertThrows(SolicitudRechazadaSinRespuestaException.class, 
				() -> this.solicitudService.denegarSolicitud(SOLICITUD_ACEPTADA_ID, respuesta));
	}

	@Test
	@DisplayName("Test Buscar solicitud de vendedor")
	void testBuscarSolicitudPorProveedorId() {
		List<Solicitud> solicitud = this.solicitudService.getsolicitudesByProvider(VENDEDOR_ID, 0, 100, "id").getContent();
		assertThat(solicitud.size()).isEqualTo(5);
		assertThat(solicitud.get(0).getId()).isEqualTo(1);
		assertThat(solicitud.get(1).getId()).isEqualTo(2);
		assertThat(solicitud.get(0).getMarca()).isEqualTo("MSI");
		assertThat(solicitud.get(1).getMarca()).isEqualTo("Lenovo");
		
		assertFalse(solicitud.get(0).getDescripcion().isEmpty());
		assertTrue(solicitud.get(0).getRespuesta().isEmpty());
	}
	
	@Test
	@DisplayName("Test Eliminar solicitud")
	void testEliminarSolicitud() {
		Solicitud solicitud = this.solicitudService.detallesSolicitud(SOLICITUD_PENDIENTE_ID);
		solicitudService.eliminarSolicitud(solicitud);
		List<Solicitud> aux = solicitudService.solicitudesPendientes(0, 1000, "-id").getContent();
		assertFalse(aux.contains(solicitud));
	}
}
