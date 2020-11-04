package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Moderador;

public interface ModeradorRepository extends CrudRepository<Moderador, Integer> {

}
