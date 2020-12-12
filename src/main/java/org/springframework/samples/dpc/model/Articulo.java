package org.springframework.samples.dpc.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "articulos")
public class Articulo extends Producto{
	
	@OneToOne(optional=true)
	private Oferta oferta;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "articulo", fetch = FetchType.EAGER)
	private Collection<Comentario> comentarios;
	
	@Column(name = "urlImagen")
	@URL
	private String urlImagen;
	
	@Column(name = "precio")
	@Min(1)
	@Max(10000)
	private Double precio;
	
	@Column(name = "stock")
	@Min(0)
	@Max(500)
	private Integer stock;
	
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	@Column(name="gastoEnvio")
	@Min(0)
	private Double gastoEnvio;
	
	@Column(name = "tiempoEntrega")
	@Min(1)
	@Max(30)
	private Integer tiempoEntrega;
	

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

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Collection<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Collection<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
}
