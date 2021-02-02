package org.springframework.samples.dpc.web;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dpc.configuration.SecurityConfiguration;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.MensajeService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=MensajeController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MensajeControllerTest {
	
	private static final int TEST_VENDEDOR_ID = 1;
	private static final int TEST_CLIENTE_ID = 1;
	
	@MockBean 
	private MensajeService mensajeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Mensaje mensaje;
	
	@BeforeEach
	void setup() {
		
		Cliente c = new Cliente();
		Bloqueo bw = new Bloqueo();
		bw.setId(4);
		bw.setBloqueado(false);
		bw.setDescripcion("");
		c.setId(TEST_CLIENTE_ID);
		c.setVersion(1);
		c.setDni("56789876");
		c.setNombre("Quique");
		c.setApellido("Salazar");
		c.setDireccion("C/Calle");
		c.setTelefono("615067389");
		c.setEmail("mail@mail.com");
		c.setBloqueo(bw);
		Vendedor vend = new Vendedor();
		vend = new Vendedor();
		vend.setId(TEST_VENDEDOR_ID);
		vend.setDni("56789876");
		vend.setNombre("Quique");
		vend.setApellido("Salazar");
		vend.setDireccion("C/Calle");
		vend.setTelefono("615067389");
		vend.setEmail("mail@mail.com");
		Bloqueo b = new Bloqueo();
		b.setId(1);
		b.setBloqueado(false);
		b.setDescripcion("");
		vend.setBloqueo(b);
		mensaje = new Mensaje();
		mensaje.setId(10);
		mensaje.setCliente(c);
		mensaje.setDestinatario("56789876");
		mensaje.setEmisor("56789876");
		mensaje.setFechaEnvio(LocalDateTime.now());
		mensaje.setTexto("Hola hola");
		mensaje.setVendedor(vend);
		mensaje.setLectura("11");
		mensaje.setVersion(1);
		
	}

}
