package org.springframework.samples.petclinic.model;

import javax.persistence.Column;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
public class Persona extends BaseEntity{
	
	@Column(name = "dni")
	@Digits(fraction = 0, integer = 8)
	protected int dni;

	@Column(name = "telefono")
	@NotEmpty
	@Digits(fraction = 0, integer = 9)
	protected String telefono;
	
	@Column(name = "direccion")
	@NotEmpty
	protected String direccion;
	
	@Column(name = "nombre")
	@NotEmpty
	protected String nombre;
	
	@Column(name = "apellido")
	@NotEmpty
	protected String apellido;

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


}
