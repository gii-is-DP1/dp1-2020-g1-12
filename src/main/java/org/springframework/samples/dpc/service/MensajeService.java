package org.springframework.samples.dpc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeService {

	private MensajeRepository mensajeRepository;
	private VendedorService vendedorService;
	private ClienteService clienteService;

	@Autowired
	public MensajeService(MensajeRepository mensajeRepository, VendedorService vendedorService, ClienteService clienteService) {
		this.mensajeRepository = mensajeRepository;
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
	}

	@Transactional(readOnly = true)
	public Pair<List<Mensaje>, List<String>> obtenerMensajes(String rol, Integer id) {
		List<Mensaje> mensajes = new ArrayList<>();
		
		if(rol.equals("cliente")) {
			Cliente cliente = clienteService.getClienteDeSesion();
			Vendedor vendedor = vendedorService.vendedorDeUnArticulo(id);
			mensajes = mensajeRepository.getMensajes(cliente, vendedor);
			List<String> aux = new ArrayList<>();
			aux.add(cliente.getDni());
			aux.add(vendedor.getNombre() + " " + vendedor.getApellido());
			return Pair.of(mensajes, aux);
		}
		Cliente cliente = clienteService.findClientById(id);
		Vendedor vendedor = vendedorService.getVendedorDeSesion();
		mensajes = mensajeRepository.getMensajes(cliente, vendedor);
		List<String> aux = new ArrayList<>();
		aux.add(vendedor.getDni());
		aux.add(cliente.getNombre() + " " + cliente.getApellido());
		return Pair.of(mensajes, aux);
	}
}
