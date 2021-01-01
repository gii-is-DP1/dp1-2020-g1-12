package org.springframework.samples.dpc.repository;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.TarjetaCredito;

public interface TarjetaRepository extends CrudRepository<TarjetaCredito, Integer> {

	@Query("select u from TarjetaCredito u where (u.numero = :numero "
			+ "and u.cvv = :cvv and u.titular = :titular and u.fechaCaducidad = :fechaCaducidad)")
	Optional<TarjetaCredito> existeTarjeta(@Param("numero") String numero, @Param("cvv") String cvv,
			@Param("titular") String titular, @Param("fechaCaducidad") String fechaCaducidad) throws DataAccessException;
	
	@Query("select u from TarjetaCredito u where u.numero = :numero")
	TarjetaCredito findTarjetaByNumber(@Param("numero") String numero) throws DataAccessException;
	
	@Query(value="SELECT COUNT(*) FROM CLIENTES_TARJETAS WHERE CLIENTES_TARJETAS.tarjetas_id= :tarjetaId", nativeQuery=true)
	Integer tarjetaCompartida(@Param("tarjetaId") int tarjetaId) throws DataAccessException;
}
