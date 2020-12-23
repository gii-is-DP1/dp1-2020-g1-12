package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tarjetasCredito")
public class TarjetaCredito extends BaseEntity{
	
	@Column(name = "titular")
	@Length(min = 8, max = 50)
	private String titular;
	
	@Column(name = "numero")
	//@Digits(fraction = 0, integer = 9)
	@Length(min = 16, max = 16)
	private String numero;
	
	@Column(name = "cvv")
	//@Digits(fraction = 0, integer = 9)
	@Length(min = 3, max = 3)
	private String cvv;

	@Column(name = "fechaCaducidad")
	private String fechaCaducidad;

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
}
