package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {

	@Query("select u from Comentario u where u.articulo.id = :articuloId")
	List<Comentario> findByArticulo(@Param("articuloId") int articuloId);
}
