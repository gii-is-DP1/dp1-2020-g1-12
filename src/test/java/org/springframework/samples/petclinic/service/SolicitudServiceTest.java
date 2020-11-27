package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))


public class SolicitudServiceTest {
	
	@Autowired
	private SolicitudService solicitudService;
	
//	@Test
//	void shouldFindOwnersByLastName() {
//		Collection<Owner> owners = this.solicitudService.findOwnerByLastName("Davis");
//		assertThat(owners.size()).isEqualTo(2);
//
//		owners = this.ownerService.findOwnerByLastName("Daviss");
//		assertThat(owners.isEmpty()).isTrue();
//	}
//
//	@Test
//	void shouldFindSingleOwnerWithPet() {
//		Owner owner = this.ownerService.findOwnerById(1);
//		assertThat(owner.getLastName()).startsWith("Franklin");
//		assertThat(owner.getPets().size()).isEqualTo(1);
//		assertThat(owner.getPets().get(0).getType()).isNotNull();
//		assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
//	}
	
	

}
