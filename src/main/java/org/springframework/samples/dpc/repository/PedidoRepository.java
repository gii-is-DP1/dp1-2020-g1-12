package org.springframework.samples.dpc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dpc.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

}
