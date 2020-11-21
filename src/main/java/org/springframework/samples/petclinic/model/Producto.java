package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public class Producto extends BaseEntity{
	
	@Column(name = "modelo")
	@NotEmpty
	@Length(min=3,max=200)
	protected String modelo;
	
	@Column(name = "marca")
	@NotEmpty
	@Length(min=3,max=50)
	protected String marca;

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	

}
