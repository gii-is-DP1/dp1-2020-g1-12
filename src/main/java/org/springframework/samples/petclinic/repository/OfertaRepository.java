package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Oferta;

public interface OfertaRepository extends CrudRepository<Oferta, Integer> {

}
