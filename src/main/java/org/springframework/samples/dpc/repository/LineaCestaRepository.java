package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.LineaCesta;

public interface LineaCestaRepository extends CrudRepository<LineaCesta, Integer> {

	@Query("select u from LineaCesta u where u.cesta.id = :cestaId")
	List<LineaCesta> findByCesta(@Param("cestaId") int cestaId);

}
