package org.springframework.samples.dpc.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.samples.dpc.util.ContrasenyaConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User{
	@Id
	@Length(min = 3, max = 20, message = "El usuario debe estar comprendido entre 3 y 20 caracteres")
	String username;
	
	@ContrasenyaConstraint
	String password;
	
	@ContrasenyaConstraint
	String newPassword;
	
	boolean enabled;

}
