package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "articulos")
public class Articulo extends Producto{
	
	@Column(name = "urlImagen")
	//@URL
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
	
	@Column(name="gastoEnvio")//Realmente queremos que esto se pueda cambiar? en ese caso quitar el set
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

}
