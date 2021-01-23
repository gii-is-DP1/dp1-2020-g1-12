package org.springframework.samples.dpc.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public class Producto extends BaseEntity {
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private Set<Genero> generos;
	
	@Column(name = "modelo")
	@Length(min=3,max=200)
	protected String modelo;
	
	@Column(name = "marca")
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

	public Set<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(Set<Genero> generos) {
		this.generos = generos;
	}
}
