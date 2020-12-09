package org.springframework.samples.dpc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tarjetaCredito")
public class TarjetaCredito extends BaseEntity{
	
	@Column(name = "titular")
	@Length(min = 20, max = 250)
	private String titular;
	
	@Column(name = "bin")
	@Digits(fraction = 0, integer = 9)
	@Length(min = 16, max = 16)
	private String bin;
	
	@Column(name = "cvv")
	@Digits(fraction = 0, integer = 9)
	@Length(min = 3, max = 3)
	private String cvv;

	@Column(name = "fechaCaducidad")
	private Date fechaCaducidad;

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
}
