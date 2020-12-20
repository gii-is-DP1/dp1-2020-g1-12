package org.springframework.samples.dpc.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.samples.dpc.repository.TarjetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaService {
	
	private TarjetaRepository tarjetaRepository;
	private ClienteService clienteService;
	
	
	@Autowired
	public TarjetaService(TarjetaRepository tarjetaRepository, ClienteService clienteService) {
		this.tarjetaRepository = tarjetaRepository;
		this.clienteService = clienteService;
	}
	
	@Transactional
	public void anyadirTarjeta(TarjetaCredito tarjeta) {
		Integer existe = tarjetaRepository.existeTarjeta(tarjeta.getNumero());
		Cliente cliente = clienteService.getClienteDeSesion();
		if(existe == 1) {
			tarjeta = tarjetaRepository.findTarjetaByNumber(tarjeta.getNumero());
		}
		cliente.getTarjetas().add(tarjeta);
	}
	
	@Transactional
	public TarjetaCredito findTarjetaById(int tarjetaId) {
		Optional<TarjetaCredito> tarjeta = tarjetaRepository.findById(tarjetaId);
		if(tarjeta.isPresent()) {
			return tarjeta.get();
		}else
			return null;
	}
	@Transactional
	public void eliminarTarjetaPersona(int tarjetaId) {
		Cliente cliente = clienteService.getClienteDeSesion();
		TarjetaCredito tarjeta = findTarjetaById(tarjetaId);
		if(cliente.getTarjetas().contains(tarjeta)) {
			cliente.getTarjetas().remove(tarjeta);
		}
		int nRelaciones =tarjetaRepository.tarjetaCompartida(tarjetaId);
		if(nRelaciones==0) {
			tarjetaRepository.delete(tarjeta);
		}
		
	}
	
}
