package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Solicitud;


public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {
	
	@Query("select u from Solicitud u where u.situacion = 'Pendiente'")
	List<Solicitud> solicitudesPendientes() throws DataAccessException;
		
	
}