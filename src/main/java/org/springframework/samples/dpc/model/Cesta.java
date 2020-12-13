package org.springframework.samples.dpc.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cestas")
public class Cesta extends BaseEntity{
	
	@Column(name = "precioFinal")
	private Double precioFinal;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cesta", fetch = FetchType.EAGER)
	private Collection<LineaCesta> lineas;

	public Collection<LineaCesta> getLineas() {
		return lineas;
	}

	public void setLineas(Collection<LineaCesta> lineas) {
		this.lineas = lineas;
	}
	
	public Double getPrecioFinal() {
		Double result = 0.;
		Double gastosEnvio = 0.;
		for(LineaCesta linea: lineas) {
			Articulo articulo = linea.getArticulo();
			if(articulo.getOferta().isDisponibilidad()) {
				result += (articulo.getPrecio() - (articulo.getOferta().getPorcentaje()*articulo.getPrecio())/100);
			}
			else {
				result += articulo.getPrecio();
			}
			gastosEnvio += articulo.getGastoEnvio();
		}
		result += gastosEnvio;
		return result;
	}
	
}
