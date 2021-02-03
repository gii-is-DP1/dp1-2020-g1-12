package org.springframework.samples.dpc.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.samples.dpc.util.ContrasenyaConstraint;

@Entity
@Table(name = "users")
public class User{
	@Id
	private String username;
	
	@ContrasenyaConstraint
	private String password;
	
	private boolean enabled;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
