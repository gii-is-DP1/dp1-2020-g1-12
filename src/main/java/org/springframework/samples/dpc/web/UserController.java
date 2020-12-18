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
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.AuthoritiesService;
import org.springframework.samples.dpc.service.BloqueoService;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private static final String VIEWS_CREATE = "users/registro";
	private static final String VIEWS_CREATE_FORM_VENDEDOR = "users/registroVendedor";
	private static final String VIEWS_CREATE_FORM_CLIENTE = "users/registroCliente";

	private final VendedorService vendedorService;
	private final ClienteService clienteService;
	private final BloqueoService bloqueoService;
	private final AuthoritiesService authoritiesService;

	@Autowired
	public UserController(VendedorService vendedorService, ClienteService clienteService, BloqueoService bloqueoService,
			AuthoritiesService authoritiesService) {
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
		this.bloqueoService = bloqueoService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/registro")
	public String initCreationForm(Map<String, Object> model) {
		return VIEWS_CREATE;
	}

	@GetMapping(value = "/registro/vendedor")
	public String initCreationFormVendedor(Map<String, Object> model) {
		Vendedor vendedor = new Vendedor();
		model.put("vendedor", vendedor);
		return VIEWS_CREATE_FORM_VENDEDOR;
	}

	@PostMapping(value = "/registro/vendedor")
	public String processCreationFormVendedor(@Valid Vendedor vendedor, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CREATE_FORM_VENDEDOR;
		} else {
			Bloqueo b = new Bloqueo();
			b.setBloqueado(false);
			bloqueoService.guardar(b);
			vendedor.setBloqueo(b);
			vendedor.getUser().setEnabled(true);
			vendedor.setBloqueo(b);
			this.vendedorService.guardar(vendedor);
			this.authoritiesService.saveAuthorities(vendedor.getUser().getUsername(), "vendedor");
			return "redirect:/registro";
		}
	}

	@GetMapping(value = "/registro/cliente")
	public String initCreationFormCliente(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CREATE_FORM_CLIENTE;
	}

	@PostMapping(value = "/registro/cliente")
	public String processCreationFormCliente(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CREATE_FORM_CLIENTE;
		} else {
			Bloqueo b = new Bloqueo();
			b.setBloqueado(false);
			b.setDescripcion("");
			bloqueoService.guardar(b);
			cliente.setBloqueo(b);
			cliente.getUser().setEnabled(true);
			this.clienteService.guardar(cliente);
			this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
			return "redirect:/registro";
		}
	}

}