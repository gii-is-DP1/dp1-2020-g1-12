package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "lineasCestas")
public class LineaCesta extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "cesta_id")
	private Cesta cesta;

	@Column(name = "cantidad")
	@Min(1)
	@Max(100)
	private Integer cantidad;

	@ManyToOne(optional = false)
	@JoinColumn(name = "articulo_id")
	private Articulo articulo;

	public Cesta getCesta() {
		return cesta;
	}

	public void setCesta(Cesta cesta) {
		this.cesta = cesta;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
}
