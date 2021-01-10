package org.springframework.samples.dpc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dpc.model.Cesta;

public interface CestaRepository extends CrudRepository<Cesta, Integer> {
	
}
