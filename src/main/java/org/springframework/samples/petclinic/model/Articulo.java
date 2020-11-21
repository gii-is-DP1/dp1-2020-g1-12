package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "articulos")
public class Articulo extends Producto{
	
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;
	
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
	@NotEmpty
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	@Column(name="gastoEnvio")//Realmente queremos que esto se pueda cambiar? en ese caso quitar el set
	@NotEmpty
	@Min(0)
	private Integer gastoEnvio;
	
	@Column(name = "tiempoEntrega")
	@NotEmpty
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

	public Integer getGastoEnvio() {
		return gastoEnvio;
	}

	public void setGastoEnvio(Integer gastoEnvio) {
		this.gastoEnvio = gastoEnvio;
	}
	
	
	
}
