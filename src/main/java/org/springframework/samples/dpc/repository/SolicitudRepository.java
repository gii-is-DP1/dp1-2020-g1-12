package org.springframework.samples.dpc.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Solicitud;


public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {
	
	@Query("select u from Solicitud u where u.situacion = 'Pendiente'")
	Page<Solicitud> solicitudesPendientes(Pageable pageable) throws DataAccessException;

	@Query("select u from Solicitud u where u.vendedor.id = :vendedorId")
	Page<Solicitud> findByVendedor(@Param("vendedorId") int vendedorId, Pageable pageable);
}