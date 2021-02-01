package org.springframework.samples.dpc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.repository.MensajeRepository;
import org.springframework.samples.dpc.service.exceptions.MensajeProhibidoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeService {

	private MensajeRepository mensajeRepository;
	private VendedorService vendedorService;
	private ClienteService clienteService;
	private LineaPedidoService lineaPedidoService;

	@Autowired
	public MensajeService(MensajeRepository mensajeRepository, VendedorService vendedorService,
			ClienteService clienteService, LineaPedidoService lineaPedidoService) {
		this.mensajeRepository = mensajeRepository;
		this.vendedorService = vendedorService;
		this.clienteService = clienteService;
		this.lineaPedidoService = lineaPedidoService;
	}

	@Transactional(readOnly = true)
	public Pair<List<Mensaje>, List<String>> obtenerMensajes(String rol, Integer id) throws MensajeProhibidoException {
		List<Mensaje> mensajes = new ArrayList<>();
		if(rol.equals("cliente")) {
			Cliente cliente = clienteService.getClienteDeSesion();
			if(!clienteService.getValidaChat(id, cliente.getId())) {
				throw new MensajeProhibidoException();
			}
			Vendedor vendedor = vendedorService.vendedorDeUnArticulo(id);
			mensajes = mensajeRepository.getMensajes(cliente, vendedor);
			List<String> aux = new ArrayList<>();
			aux.add(cliente.getDni());
			aux.add(vendedor.getNombre() + " " + vendedor.getApellido());
			aux.add(vendedor.getId().toString());
			return Pair.of(mensajes, aux);
		}
		Cliente cliente = clienteService.findClientById(id);
		Vendedor vendedor = vendedorService.getVendedorDeSesion();
		List<Integer> lineas = lineaPedidoService.articulosVendidosByProvider(0, Integer.MAX_VALUE, "-id", 
				vendedor.getId()).getContent().stream().map(x -> x.getArticulo().getId())
				.collect(Collectors.toList());
		if(!lineaPedidoService.esComprador(lineas, id)) {
			throw new MensajeProhibidoException();
		}
		mensajes = mensajeRepository.getMensajes(cliente, vendedor);
		List<String> aux = new ArrayList<>();
		aux.add(vendedor.getDni());
		aux.add(cliente.getNombre() + " " + cliente.getApellido());
		aux.add(cliente.getId().toString());
		return Pair.of(mensajes, aux);
	}
	
	@Transactional(rollbackFor=MensajeProhibidoException.class)
	public void enviarMensaje(Mensaje mensaje, String rol, Integer receptorId, int id) throws MensajeProhibidoException {
		if(rol.equals("cliente")) {
			if(!vendedorService.vendedorDeUnArticulo(id).getId().equals(receptorId)) {
				throw new MensajeProhibidoException();
			}
			Cliente cliente = clienteService.getClienteDeSesion();
			mensaje.setCliente(cliente);
			mensaje.setEmisor(cliente.getDni());
			Vendedor vendedor = vendedorService.findSellerById(receptorId);
			mensaje.setDestinatario(vendedor.getDni());
			mensaje.setVendedor(vendedor);
		} else {
			Vendedor vendedor = vendedorService.getVendedorDeSesion();
			List<Integer> lineas = lineaPedidoService.articulosVendidosByProvider(0, Integer.MAX_VALUE, "-id", 
					vendedor.getId()).getContent().stream().map(x -> x.getArticulo().getId())
					.collect(Collectors.toList());
			if(!lineaPedidoService.esComprador(lineas, id)) {
				throw new MensajeProhibidoException();
			}
			Cliente cliente = clienteService.findClientById(id);
			mensaje.setCliente(cliente);
			mensaje.setEmisor(vendedor.getDni());
			mensaje.setDestinatario(cliente.getDni());
			mensaje.setVendedor(vendedor);
		}
		mensaje.setFechaEnvio(LocalDateTime.now());
		mensajeRepository.save(mensaje);
	}
}
