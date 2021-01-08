package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.LineaPedido;

public interface LineaPedidoRepository extends CrudRepository<LineaPedido, Integer> {

	@Query("select u from LineaPedido u where u.pedido.id = :pedidoId")
	List<LineaPedido> findByPedido(@Param("pedidoId") int pedidoId);

	@Query(value = "select * from LINEAS_PEDIDOS where (LINEAS_PEDIDOS.articulo_id in :articulosId)", nativeQuery = true)
	Page<LineaPedido> findArticulosVendidos(@Param("articulosId") List<Integer> articulosId, Pageable pageable);
}
