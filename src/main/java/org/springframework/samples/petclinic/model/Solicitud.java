package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "solicitudes")
public class Solicitud extends BaseEntity{

	@Column(name = "descripcion")
	@NotEmpty
	private String descripcion;

	@Column(name = "stock")
	@NotEmpty
	private Integer stock;
	
	@Column(name = "situacion")
	@NotEmpty
	@Enumerated(EnumType.STRING)
	@ColumnDefault(value = "'Pendiente'")
	private Situacion situacion;
	
	@Column(name = "respuesta")
	private String respuesta;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Situacion getSituacion() {
		return situacion;
	}

	public void setSituacion(Situacion situacion) {
		this.situacion = situacion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}
