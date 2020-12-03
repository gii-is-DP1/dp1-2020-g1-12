package org.springframework.samples.dpc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dpc.model.Oferta;

public interface OfertaRepository extends CrudRepository<Oferta, Integer> {

}
