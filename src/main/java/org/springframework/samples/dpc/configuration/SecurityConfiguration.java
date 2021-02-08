package org.springframework.samples.dpc.configuration;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private String moderador = "moderador";
	private String vendedor = "vendedor";
	private String cliente = "cliente";
	
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/", "/error").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.antMatchers("/registro/**").permitAll()
				.antMatchers("/clientes").hasAnyAuthority(moderador)
				.antMatchers("/clientes/**").hasAnyAuthority(cliente)
				.antMatchers("/cesta/**").hasAnyAuthority(cliente)
				.antMatchers("/pedidos").hasAnyAuthority(cliente)
				.antMatchers("/pedidos/modificar/**").hasAnyAuthority(vendedor)
				.antMatchers("/pedidos/**").hasAnyAuthority(cliente)
				.antMatchers("/vendedores/**").hasAnyAuthority(vendedor)
				.antMatchers("/solicitudes/new").hasAnyAuthority(vendedor)
				.antMatchers("/solicitudes/save").hasAnyAuthority(vendedor)
				.antMatchers("/solicitudes").hasAnyAuthority(moderador)
				.antMatchers("/solicitudes/{solicitudId}/**").hasAnyAuthority(moderador)
				.antMatchers("/solicitudes/solicitante/**").hasAnyAuthority(moderador)
				.antMatchers("/bloqueos/**").hasAnyAuthority(moderador)
				.antMatchers("/moderadores/**").hasAnyAuthority(moderador)
				.antMatchers("/busqueda").permitAll()
				.antMatchers("/ofertas").permitAll()
				.antMatchers("/login").permitAll()	
				.antMatchers("/logout").permitAll()	
				.antMatchers("/loginForm").permitAll()			
				.antMatchers("/tarjetas/**").hasAnyAuthority(cliente)
				.antMatchers("/generos/**").hasAnyAuthority(vendedor)
				.antMatchers("/comentario/eliminar/**").hasAnyAuthority(moderador)
				.antMatchers("/comentario/**").hasAnyAuthority(cliente, moderador, vendedor)
				.antMatchers("/comentario/editar/**").hasAnyAuthority(cliente, vendedor)
				.antMatchers("/chat/cliente/**").hasAnyAuthority(cliente)
				.antMatchers("/chat/vendedor/**").hasAnyAuthority(vendedor)				
				.antMatchers("/articulos/{articuloId}").permitAll()
				.anyRequest().denyAll().and()
				.formLogin()
				.loginPage("/login") 
				.failureUrl("/login-error").and().logout();
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {	//Modificar el método para utilizarlo
	    return super.authenticationManagerBean();								//en el controlador
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
				.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?")
				.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
