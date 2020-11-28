package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Articulo;

public interface ArticuloRepository extends CrudRepository<Articulo, Integer> {

	@Query("select u.articulo from Solicitud u where u.vendedor.id = :vendedorId and u.situacion = 'Aceptada' "
			+ "and u.articulo.stock > 0")
	List<Articulo> articulosEnVentaPorId(@Param("vendedorId") Integer vendedorId) throws DataAccessException;
	
}
