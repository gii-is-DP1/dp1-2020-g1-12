package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ofertas")
public class Oferta extends BaseEntity{

	@Column(name = "disponibilidad")
	private boolean disponibilidad;
	
	@Column(name = "porcentaje")
	@Min(5)
	@Max(70)
	@NotNull(message = "El porcentaje no puede estar vacío.")
	private Integer porcentaje;

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	
	public Integer getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Integer porcentaje) {
		this.porcentaje = porcentaje;
	}
}
