package org.springframework.samples.dpc.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Moderador;

public interface ModeradorRepository extends CrudRepository<Moderador, Integer> {

	@Query("select u.id from Moderador u where u.user.username = :username")
	Integer moderadorId(@Param("username") String username) throws DataAccessException;
	
}
