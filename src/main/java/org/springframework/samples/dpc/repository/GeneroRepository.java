package org.springframework.samples.dpc.repository;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Genero;

public interface GeneroRepository extends CrudRepository<Genero, Integer>{

	@Query("select u from Genero u where u not in (:generosActuales)")
	List<Genero> generosRestantes(@Param("generosActuales") Set<Genero> generosActuales) throws DataAccessException;
	
}
