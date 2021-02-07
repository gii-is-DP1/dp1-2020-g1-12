package org.springframework.samples.dpc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Mensaje;
import org.springframework.samples.dpc.model.Vendedor;

public interface MensajeRepository extends CrudRepository<Mensaje, Integer> {

	@Query("select u from Mensaje u where u.cliente =:cliente and u.vendedor =:vendedor order by u.fechaEnvio")
	List<Mensaje> getMensajes(@Param("cliente") Cliente cliente, @Param("vendedor") Vendedor vendedor);
	
	@Query("select count(u) from Mensaje u where u.cliente =:cliente and u.vendedor =:vendedor and lectura='01'")
	Integer mensajesNoLeidosCliente(@Param("cliente") Cliente cliente, @Param("vendedor") Vendedor vendedor);
	
	@Query("select count(u) from Mensaje u where u.cliente =:cliente and u.vendedor =:vendedor and lectura='10'")
	Integer mensajesNoLeidosVendedor(@Param("cliente") Cliente cliente, @Param("vendedor") Vendedor vendedor);
	
	@Modifying
	@Query("update Mensaje u set u.lectura = '11' where u.cliente =:cliente and u.vendedor =:vendedor")
	void confirmarLectura(@Param("cliente") Cliente cliente, @Param("vendedor") Vendedor vendedor);
}
