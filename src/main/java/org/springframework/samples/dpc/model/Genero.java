package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "generos")
public class Genero extends BaseEntity {
	
	@Column(name = "nombre")
	@Length(min = 3, max = 50, message = "El nombre del género debe contener entre 3 y 50 caracteres.")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
