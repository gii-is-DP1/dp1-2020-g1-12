package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "producto")
public class Producto extends BaseEntity{
	
	@Column(name = "modelo")
	@NotEmpty
	@Length(min=3,max=20)
	protected String modelo;
	
	@Column(name = "marca")
	@NotEmpty
	@Length(min=3,max=20)
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
