package org.springframework.samples.dpc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dpc.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}
