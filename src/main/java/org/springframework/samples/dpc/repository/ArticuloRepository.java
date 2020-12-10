package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Articulo;

public interface ArticuloRepository extends CrudRepository<Articulo, Integer> {

	@Query("select u.articulo from Solicitud u where u.vendedor.id = :vendedorId and u.situacion = 'Aceptada' "
			+ "and u.articulo.stock > 0")
	List<Articulo> articulosEnVentaPorId(@Param("vendedorId") Integer vendedorId) throws DataAccessException;
	
	@Query("select u from Articulo u where u.stock > 0")
	List<Articulo> articulosDisponibles() throws DataAccessException;
	
	
	@Query(value = "SELECT * FROM ARTICULOS WHERE UPPER(ARTICULOS.marca || ' ' || ARTICULOS.modelo) LIKE '%' || UPPER(:nombre) || '%' ", nativeQuery = true)
	List<Articulo> articulosPorNombre(@Param("nombre") String nombre) throws DataAccessException;
	
	@Query(value = "select * from Articulos where Articulos.id in (select ARTICULOS_GENEROS.articulo_id from ARTICULOS_GENEROS where ARTICULOS_GENEROS.generos_id IN :generosId)", nativeQuery = true)
	List<Articulo> articulosPorGenero(@Param("generosId") List<Integer> generosId) throws DataAccessException;
	
	@Query(value = "select * from Articulos where (Articulos.id in (select ARTICULOS_GENEROS.articulo_id from ARTICULOS_GENEROS where ARTICULOS_GENEROS.generos_id IN :generosId)) "
			+ "and (UPPER(ARTICULOS.marca || ' ' || ARTICULOS.modelo) LIKE '%' || UPPER(:nombre) || '%')", nativeQuery = true)
	List<Articulo> articulosPorGeneroNombre(@Param("generosId") List<Integer> generosId, @Param("nombre") String nombre) throws DataAccessException;
	


}