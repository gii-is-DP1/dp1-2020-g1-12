package org.springframework.samples.dpc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dpc.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {

}
