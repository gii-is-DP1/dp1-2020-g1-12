package org.springframework.samples.dpc.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("select u.id from Cliente u where u.user.username = :username")
	Integer clienteId(@Param("username") String username) throws DataAccessException;

	@Query("SELECT DISTINCT u FROM Cliente u WHERE u.dni LIKE :dni%")
	Cliente findByDni(@Param("dni") String dni) throws DataAccessException;

	@Query("select u.bloqueo from Cliente u where u.user.username = :username")
	Bloqueo clienteBloqueo(@Param("username") String username) throws DataAccessException;
	
	@Query("select u from Cliente u")
	Page<Cliente> findAll(Pageable pageable) throws DataAccessException;
	
	@Query(value="SELECT COUNT(*) FROM PEDIDOS, LINEAS_PEDIDOS WHERE cliente_id =:clienteId AND articulo_id =:articuloId", nativeQuery=true)
	Integer haCompradoArticulo(@Param("articuloId") Integer articuloId, @Param("clienteId") Integer clienteId) throws DataAccessException;
	
}
