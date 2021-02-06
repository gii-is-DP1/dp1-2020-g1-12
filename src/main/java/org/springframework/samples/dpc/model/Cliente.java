package org.springframework.samples.dpc.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;


@Entity
@Table(name = "clientes")
public class Cliente extends Persona{
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private Set<TarjetaCredito> tarjetas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private Collection<Mensaje> mensajes;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
	private Collection<Pedido> pedidos;
	
	@OneToOne(cascade = CascadeType.ALL,optional=false)
	private Cesta cesta;
	
	@Column(name = "email")
	@Email
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL, optional=false)
	private Bloqueo bloqueo;

	public Cesta getCesta() {
		return cesta;
	}

	public void setCesta(Cesta cesta) {
		this.cesta = cesta;
	}
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Collection<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Set<TarjetaCredito> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(Set<TarjetaCredito> tarjetas) {
		this.tarjetas = tarjetas;
	}

	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Collection<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
}
