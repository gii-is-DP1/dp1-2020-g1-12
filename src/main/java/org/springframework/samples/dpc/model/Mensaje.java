package org.springframework.samples.dpc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "mensajes")
public class Mensaje extends BaseEntity{
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Column(name = "emisor")
	@Digits(fraction = 0, integer = 8, message = "Debe de estar formado solo por números")
	@Length(min = 8, max = 8, message = "El número de DNI debe estar formado por 8 dígitos")
	private String emisor;

	@Column(name = "destinatario")
	@Digits(fraction = 0, integer = 8, message = "Debe de estar formado solo por números")
	@Length(min = 8, max = 8, message = "El número de DNI debe estar formado por 8 dígitos")
	private String destinatario;

	@Column(name = "texto")
	@Length(min = 0, max = 100)
	private String texto;
	
	@Column(name = "fechaEnvio")
	private LocalDateTime fechaEnvio;
	
	@Column(name = "lectura")
	@Pattern(regexp = "^[0-1]{2,2}$")
	private String lectura;

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getLectura() {
		return lectura;
	}

	public void setLectura(String lectura) {
		this.lectura = lectura;
	}
}
