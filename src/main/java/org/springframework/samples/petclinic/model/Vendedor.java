package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "vendedores")
public class Vendedor extends Persona {
	
	@Column(name = "email")
	@Email
	private String email;
	
	@OneToOne(optional=false)
	private Bloqueo bloqueo;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Bloqueo getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(Bloqueo bloqueo) {
		this.bloqueo = bloqueo;
	}
}
