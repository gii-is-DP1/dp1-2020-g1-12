package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

	@Query("select u from Pedido u where u.cliente.id = :clienteId")
	Page<Pedido> findByCliente(@Param("clienteId") int clienteId, Pageable pageable);

//	@Query("select u from Pedido u")
//	List<Pedido> findAll() throws DataAccessException;
}