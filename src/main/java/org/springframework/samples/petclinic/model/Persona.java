package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public class Persona extends BaseEntity {

	@Column(name = "dni")
	@Digits(fraction = 0, integer = 8, message = "Debe de estar formado solo por números")
	@Length(min = 8, max = 8, message = "El número de teléfono debe estar formado por 9 dígitos")
	protected String dni;

	@Column(name = "telefono")
	@Digits(fraction = 0, integer = 9, message = "Debe de estar formado solo por números")
	@Length(min = 9, max = 9, message = "El número de teléfono debe estar formado por 9 dígitos")
	protected String telefono;

	@Column(name = "direccion")
	@Length(min = 3, max = 20)
	protected String direccion;

	@Column(name = "nombre")
	@Length(min = 3, max = 20)
	protected String nombre;

	@Column(name = "apellido")
	@Length(min = 3, max = 20)
	protected String apellido;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
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

	@Override
	public String toString() {
		return "Persona [dni=" + dni + ", telefono=" + telefono + ", direccion=" + direccion + ", nombre=" + nombre
				+ ", apellido=" + apellido + "]";
	}

}
