package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.LineaPedido;

public interface LineaPedidoRepository extends CrudRepository<LineaPedido, Integer> {

	@Query("select u from LineaPedido u where u.pedido.id = :pedidoId")
	List<LineaPedido> findByPedido(@Param("pedidoId") int pedidoId);

	@Query("select u from LineaPedido u")
	Page<LineaPedido> findAll(Pageable pageable) throws DataAccessException;

}
