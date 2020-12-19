package org.springframework.samples.dpc.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.TarjetaCredito;

public interface TarjetaRepository extends CrudRepository<TarjetaCredito, Integer> {

	@Query("select count(u) from TarjetaCredito u where u.numero = :numero")
	Integer existeTarjeta(@Param("numero") String numero) throws DataAccessException;
	
	@Query("select u from TarjetaCredito u where u.numero = :numero")
	TarjetaCredito findTarjetaByNumber(@Param("numero") String numero) throws DataAccessException;
}
