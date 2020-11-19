package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Vendedor;

public interface VendedorRepository extends CrudRepository<Vendedor, Integer> {

}
