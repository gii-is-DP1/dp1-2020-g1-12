package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Articulo;

public interface ArticuloRepository extends CrudRepository<Articulo, Integer> {

}
