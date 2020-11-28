package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	
	@Query("select u.id from Cliente u where u.user.username = :username")
	Integer clienteId(@Param("username") String username) throws DataAccessException;
	
}
