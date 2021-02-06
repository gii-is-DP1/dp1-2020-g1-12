/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dpc.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoCoincideException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaNoValidaException;
import org.springframework.samples.dpc.service.exceptions.ContrasenyaParecidaUsuarioException;
import org.springframework.samples.dpc.service.exceptions.UsernameDuplicadoException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	private static final String VIEWS_CREATE = "users/registro";
	private static final String VIEWS_CREATE_FORM_VENDEDOR = "users/registroVendedor";
	private static final String VIEWS_CREATE_FORM_CLIENTE = "users/registroCliente";

	private final VendedorService vendedorService;
	private final ClienteService clienteService;

	@Autowired
	public UserController(VendedorService vendedorService, ClienteService clienteService) {
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/registro")
	public String initCreationForm(Map<String, Object> model) {
		log.info("Entrando en la función Iniciar Formulario del controlador de User.");

		return VIEWS_CREATE;
	}

	@GetMapping(value = "/registro/vendedor")
	public String initCreationFormVendedor(Map<String, Object> model) {
		log.info("Entrando en la función Iniciar Formulario de Vendedor del controlador de User.");

		Vendedor vendedor = new Vendedor();
		model.put("vendedor", vendedor);
		return VIEWS_CREATE_FORM_VENDEDOR;
	}

	@PostMapping(value = "/registro/vendedor")
	public String processCreationFormVendedor(@Valid Vendedor vendedor, BindingResult result, Map<String, Object> model) throws Exception {
		log.info("Entrando en la función Proceso Formulario de Vendedor del controlador de User.");

		if (result.hasErrors()) {
			return VIEWS_CREATE_FORM_VENDEDOR;
		} else {
			try {
				this.vendedorService.registroVendedor(vendedor);
			} catch (UsernameDuplicadoException e) {
				log.warn("La función Proceso Formulario de Vendedor ha lanzado la excepción Username Duplicado");
				
				result.rejectValue("user.username", "errónea", "El nombre de usuario introducido ya está en uso o no es válido. Por favor, seleccione otro");
				return VIEWS_CREATE_FORM_VENDEDOR;
			} catch (ContrasenyaNoValidaException e) {
				log.warn("La función Proceso Formulario de Vendedor ha lanzado la excepción Contrasenya No Válida");
				
				model.put("message", "La contraseña introducida no es válida. Debe contener entre 8 y 16 caracteres y al menos una mayúscula, una minúscula y un dígito.");
				return VIEWS_CREATE_FORM_VENDEDOR;
			} catch (ContrasenyaNoCoincideException e) {
				log.warn("La función Proceso Formulario de Vendedor ha tenido un error debido a que las contraseña no coinciden.");

				model.put("message", "La contraseña introducida no coincide con la de la cuenta.");
	            return VIEWS_CREATE_FORM_VENDEDOR;
			} catch (ContrasenyaParecidaUsuarioException e) {
				log.warn("La función Proceso Formulario de Vendedor ha lanzado la excepción Contrasenya Parecida Usuario.");
				
				model.put("message", "La contraseña no puede ser idéntica al nombre de usuario.");
				return VIEWS_CREATE_FORM_VENDEDOR;
			} 
			return "redirect:/login";
		}
	}

	@GetMapping(value = "/registro/cliente")
	public String initCreationFormCliente(Map<String, Object> model) {
		log.info("Entrando en la función Iniciar Formulario de Cliente del controlador de User.");

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CREATE_FORM_CLIENTE;
	}

	@PostMapping(value = "/registro/cliente")
	public String processCreationFormCliente(@Valid Cliente cliente, BindingResult result, Map<String, Object> model) throws Exception {
		log.info("Entrando en la función Proceso Formulario de Cliente del controlador de User.");

		if (result.hasErrors()) {
			return VIEWS_CREATE_FORM_CLIENTE;
		} else {
			try {
				this.clienteService.registroCliente(cliente);
			} catch (UsernameDuplicadoException e) {
				log.warn("La función Proceso Formulario de Cliente ha lanzado la excepción Username Duplicado");
				result.rejectValue("user.username", "errónea", "El nombre de usuario introducido ya está en uso o no es válido. Por favor, seleccione otro");
				return VIEWS_CREATE_FORM_CLIENTE;
			} catch (ContrasenyaNoValidaException e) {
				log.warn("La función Proceso Formulario de Cliente ha lanzado la excepción Contrasenya No Válida");
				
				model.put("message", "La contraseña introducida no es válida. Debe contener entre 8 y 16 caracteres y al menos una mayúscula, una minúscula y un dígito.");
				return VIEWS_CREATE_FORM_CLIENTE;
			} catch (ContrasenyaNoCoincideException e) {
				log.warn("La función Proceso Formulario de Cliente ha lanzado la excepción Contrasenya No Coincide.");
				
				model.put("message", "Las contraseñas introducidas no coinciden.");
	            return VIEWS_CREATE_FORM_CLIENTE;
			} catch (ContrasenyaParecidaUsuarioException e) {
				log.warn("La función Proceso Formulario de Cliente ha lanzado la excepción Contrasenya Parecida Usuario.");
				
				model.put("message", "La contraseña no puede ser idéntica al nombre de usuario.");
				return VIEWS_CREATE_FORM_CLIENTE;
			} 
			return "redirect:/login";
		}
	}

}