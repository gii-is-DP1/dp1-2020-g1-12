package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "solicitudes")
public class Solicitud extends BaseEntity {

	@OneToOne(optional = true)
	private Articulo articulo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;

	@Column(name = "descripcion")
	@Length(min = 10, max = 5000, message = "La descripción debe contener entre 10 y 5000 caracteres.")
	private String descripcion;

	@Column(name = "modelo")
	@Length(min = 3, max = 200, message = "El modelo debe contener entre 3 y 200 caracteres.")
	private String modelo;

	@Column(name = "marca")
	@Length(min = 3, max = 50, message = "La marca debe contener entre 3 y 50 caracteres.")
	private String marca;

	@Column(name = "urlImagen")
	@URL
	@NotEmpty(message="La Url no puede estar vacía.")
	private String urlImagen;

	@Column(name = "precio")
	@Min(1)
	@Max(10000)
	@NotNull(message="El precio no puede estar vacío.")
	private Double precio;

	@Column(name = "stock")
	@Min(1)
	@Max(500)
	@NotNull(message="El stock no puede estar vacío.")
	private Integer stock;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	@NotNull(message="El tipo no puede estar vacío.")
	private Tipo tipo;

	@Column(name = "tiempoEntrega")
	@Min(1)
	@Max(30)
	@NotNull(message="El tiempo de entrega no puede estar vacío.")
	private Integer tiempoEntrega;

	@Column(name = "gastoEnvio")
	@Min(0)
	@NotNull(message="El gasto de envío no puede estar vacío.")
	private Double gastoEnvio;

	@Column(name = "situacion")
	@Enumerated(EnumType.STRING)
	private Situacion situacion;

	@Column(name = "respuesta")
	@Length(min = 0, max = 100)
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

	public Double getGastoEnvio() {
		return gastoEnvio;
	}

	public void setGastoEnvio(Double gastoEnvio) {
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

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
}
