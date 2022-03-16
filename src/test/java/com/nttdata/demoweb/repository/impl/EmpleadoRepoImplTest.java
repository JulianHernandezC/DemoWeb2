package com.nttdata.demoweb.repository.impl;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.demoweb.repository.EmpleadoRepoJPA;
import com.nttdata.demoweb.repository.entity.Empleado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class EmpleadoRepoImplTest {
	private Integer idEmpleado;

	@Autowired
	EmpleadoRepoJPA repo;
	
	
	@BeforeEach
	void setUp() throws Exception {
		//Borramos todos los empleados de BBDD:
		repo.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testListarCuyoNombreContiene() {
		//GIVEN:
			Empleado e1 = new Empleado();
			e1.setNombre("Lucas");
			e1.setApellidos("Ape1 Ape2");
			/*e1 =*/ repo.save(e1);
			//idEmpleado = e1.getId();
		
			Empleado e2 = new Empleado();
			e2.setNombre("Ana");
			e2.setApellidos("Ape1 Ape2");
			e2=repo.save(e2);
		
		//WHEN:
			List<Empleado> l1 = repo.listarCuyoNombreContiene("u");
			List<Empleado> l2 = repo.listarCuyoNombreContiene("8");
			List<Empleado> l3 = repo.listarCuyoNombreContiene("U");
		
		//THEN:
			assertEquals(e1, l1.get(0), "e1 es el empleado con 'u'");
			assertAll (
					() -> assertEquals(1, l1.size(), "Solo 1 empleado contiene una 'u'"),
					//() -> assertEquals(repo.getById(idEmpleado), l1.get(0), "e1 es el empleado con 'u'"),
					() -> assertEquals(0, l2.size(), "Ningún empleado contiene un '8'"),
					() -> assertEquals(0, l3.size(), "Ningún empleado contiene una 'U'")
			);
			
			//Dejarlo todo como estaba:
			//repo.delete(e1);
			//repo.delete(e2);

	}

	@Test
	void testRegistrar() {
		repo.registrar("Mensaje prueba");
	}

}
