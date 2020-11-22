package org.springframework.samples.petclinic.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "solicitudes")
public class Solicitud extends BaseEntity {

	@Column(name = "descripcion")
	@NotEmpty
	@Length(min=3,max=250)
	private String descripcion;
	
	@Column(name = "modelo")
	@NotEmpty
	@Length(min=3,max=20)
	private String modelo;
	
	@Column(name = "marca")
	@NotEmpty
	@Length(min=3,max=20)
	private String marca;
	
	@Column(name = "urlImagen")
	//@URL
	private String urlImagen;
	
	@Column(name = "precio")
	@Min(1)
	@Max(10000)
	private Double precio;
	
	@Column(name = "stock")
	@Min(1)
	@Max(500)
	private Integer stock;
	
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	@Column(name = "tiempoEntrega")
	@Min(1)
	@Max(30)
	private Integer tiempoEntrega;
	
	@Column(name="gastoEnvio")
	@Min(0)
	private Integer gastoEnvio;

	@Column(name = "situacion")
	@Enumerated(EnumType.STRING)
	private Situacion situacion;
	
	@Column(name = "respuesta")
	@Length(min=0,max=100)
	private String respuesta;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

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

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Integer getTiempoEntrega() {
		return tiempoEntrega;
	}

	public void setTiempoEntrega(Integer tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}

	public Integer getGastoEnvio() {
		return gastoEnvio;
	}

	public void setGastoEnvio(Integer gastoEnvio) {
		this.gastoEnvio = gastoEnvio;
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
