package org.springframework.samples.dpc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dpc.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
