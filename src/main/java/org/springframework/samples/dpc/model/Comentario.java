package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "comentario")
public class Comentario extends BaseEntity{

	@Column(name = "descipcion")
	@Length(min = 50, max = 200)
	protected String descipcion;
	
	@Column(name = "valoracion")
	@Min(0)
	@Max(10)
	private Integer valoracion;

	public String getDescipcion() {
		return descipcion;
	}

	public void setDescipcion(String descipcion) {
		this.descipcion = descipcion;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}
}
