package org.springframework.samples.dpc.model;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "vendedores")
public class Vendedor extends Persona {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

	@Column(name = "email")
	@Email
	private String email;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private Bloqueo bloqueo;

	public String getEmail() {
		return email;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedor")
	private Collection<Mensaje> mensajes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedor", fetch = FetchType.EAGER)
	private Collection<Solicitud> solicitudes;

	public void setEmail(String email) {
		this.email = email;
	}

	public Bloqueo getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(Bloqueo bloqueo) {
		this.bloqueo = bloqueo;
	}

	public Collection<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Collection<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}
	
	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Collection<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
