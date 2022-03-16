package com.nttdata.demoweb.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.demoweb.repository.EmpleadoRepoJPA;
import com.nttdata.demoweb.repository.entity.Empleado;
import com.nttdata.demoweb.service.EmpleadoService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class EmpleadoServiceImplTest {
	private Empleado e1, e2;
	
	@Autowired
	EmpleadoRepoJPA repo;
	
	@Autowired
	EmpleadoService service;

	@BeforeEach
	void setUp() throws Exception {
		repo.deleteAll();
		
		e1 = new Empleado();
		e1.setNombre("Manuel");
		e1.setApellidos("Muñoz Martínez");
		e1=repo.save(e1);
	
		e2 = new Empleado();
		e2.setNombre("Ana");
		e2.setApellidos("Alexa Armani");
		e2=repo.save(e2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRegistrar() {
		service.registrar("Mensaje prueba");
	}

	@Test
	void testListar() {
		//GIVEN:
			//Existen dos empleados
			assertEquals(2, service.listar().size(), "2 emp en BBDD");
		
		//WHEN:
			List<Empleado> le = service.listar();
		
		//THEN:
			assertEquals(2, le.size(), "Hay 2 empleados en BBDD");
			/*int encontrados=0;
			for(Empleado e: le) {
				if(e==e1 || e==e2)
					encontrados++;
			}
			assertEquals(2, encontrados, "Encontrados e1 y e2");*/
	}

	@Test
	void testListarFiltroNombre() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");

		//WHEN:
			List<Empleado> le = service.listarFiltroNombre("u");
		
		//THEN:
			assertEquals(1, le.size(), "Solo está 'Manuel' con una 'u' en el nombre");
			assertEquals(e1, le.get(0), "Manuel contiene una 'u'");
	}

	@Test
	void testListarConJPA() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
	
		//WHEN:
			List<Empleado> le = service.listarFiltroNombre("%a%");
		
		//THEN:
			assertEquals(2, le.size(), "Los dos empleados tienen 'a' en el nombre.");
	}

	@Test
	void testListarFiltroNombreEs() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
	
		//WHEN:
			List<Empleado> le1 = service.listarFiltroNombreEs("Ana");
			List<Empleado> le2 = service.listarFiltroNombreEs("u");
			
		//THEN:
			assertAll(
					 () -> assertEquals(1, le1.size(), "Hay 1 empleado Ana"),
					 () -> assertEquals(0, le2.size(), "Hay 0 empleados que se llamen 'u'")
				 );				
	}

	
	@Test
	void testInserta() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
	
		//WHEN:
			Empleado e3 = new Empleado();
			e3.setNombre("N3");
			e3.setApellidos("AP3");
			e3 = service.inserta(e3);
			
		//THEN:
			assertEquals(3, service.listar().size(), "Hay 3 empleados");
	}

	
	
	@Test
	void testModificar() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
	
		//WHEN:
			String nuevoNombre="Marianico";
			e2.setNombre(nuevoNombre);
			service.modificar(e2);
			
		//THEN:
			assertEquals(2, service.listar().size(), "Sigue habiendo dos empleados en BBDD");
			assertEquals (nuevoNombre, service.getById(e2.getId()).getNombre(), "Mod nombre" );
	}

	@Test
	void testEliminar() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
			
		//WHEN:
			service.eliminar( e1.getId() );
			
		//THEN:
			assertEquals(1, service.listar().size(), "Solo queda 1 usuario");
	}

	
	@Test
	void testGetById() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
			
		//WHEN:
			Empleado e3 = service.getById( e1.getId() );
			
		//THEN:
			assertEquals (e1.getId(), e3.getId(), "Mismo empleado");
			assertNotNull (e3, "Empleado válido");
	}

}
