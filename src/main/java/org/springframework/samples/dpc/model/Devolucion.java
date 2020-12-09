package org.springframework.samples.dpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "devolucion")
public class Devolucion extends BaseEntity{

	@Column(name = "motivo")
	@Length(min = 50, max = 200)
	private String motivo;

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	@OneToOne(optional=false)
	private LineaPedido linea;
	
}
