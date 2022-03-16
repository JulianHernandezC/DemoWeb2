package com.nttdata.demoweb.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.demoweb.repository.EmpleadoRepoJPA;
import com.nttdata.demoweb.repository.entity.Empleado;
import com.nttdata.demoweb.service.EmpleadoService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class EmpleadoRestControllerTest {
	private Empleado e1, e2;
	
	@Autowired
	EmpleadoRepoJPA repo;
	
	@Autowired
	EmpleadoService service;
	
	@Autowired
	EmpleadoRestController restcontroller;
	
	@Mock // --> simular
	EmpleadoService serviceMock;
	
	@InjectMocks
	EmpleadoRestController restcontrollerMock;
	
	

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
		repo.deleteAll();
	}

	@Test
	void testListarEmpleados() {
		//GIVEN:
			//Existen dos empleados
			assertEquals(2, service.listar().size(), "2 emp en BBDD");
		
		//WHEN:
			List<Empleado> le = restcontroller.listarEmpleados();
		
		//THEN:
			assertEquals(2, le.size(), "Hay 2 empleados en BBDD");
	}
	
	@Test
	void testDevuelveEmpleado() {
		//GIVEN:
			//Existen dos empleados
			assertEquals(2, service.listar().size(), "2 emp en BBDD");
		
		//WHEN:
			ResponseEntity<Empleado> re = restcontroller.devuelveEmpleado( e1.getId() );
			
		//THEN:
			assertEquals(e1, re.getBody(), "Empleado e1" );
			assertThat ( re.getStatusCodeValue() ).isEqualTo(200);
			assertThat ( re.getStatusCode()).isEqualTo( HttpStatus.OK);
	}
	
	@Test
	void testModificarEmpleado() {
		//GIVEN:
			//Existen dos empleados
			assertEquals(2, service.listar().size(), "2 emp en BBDD");
		
		//WHEN:
			String nuevoNombre="Marianico";
			e2.setNombre(nuevoNombre);
			restcontroller.modificarEmpleado(e2);
		
		//THEN:
			assertEquals(2, service.listar().size(), "Sigue habiendo dos empleados en BBDD");
			assertEquals (nuevoNombre, service.getById(e2.getId()).getNombre(), "Modidicado nombre" );
	}
	
	@Test
	//@WithMockUser(username="user1", roles= {"ADMIN"})
	void testEliminarEmpleado() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
			
		//WHEN:
			restcontroller.eliminarEmpleado( e1.getId() );
			
		//THEN:
			assertEquals(1, service.listar().size(), "Solo queda 1 usuario");
		
	}
	
	@Test
	void testInsertarEmpleado_v3_idIsNotNull() {
		//GIVEN:
		
		//WHEN:
			Empleado e3 = new Empleado();
			e3.setId(88);
			e3.setNombre("Nombre");
			e3.setApellidos("apellidos");
			ResponseEntity<Empleado> re = restcontroller.insertarEmpleado_v3(e3);
			
		//THEN:
			assertEquals (HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Error 406 id is not null");		
	}
	
	@Test
	void testInsertarEmpleado_v3_NombreIsNull() {
		//GIVEN:
		
		//WHEN:
			Empleado e3 = new Empleado();
			e3.setApellidos("apellidos");
			ResponseEntity<Empleado> re = restcontroller.insertarEmpleado_v3(e3);
			
		//THEN:
			assertEquals (HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Error 406 nombre is null");		
	}

	
	@Test
	void testInsertarEmpleado_v3() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
		
		//WHEN:
			Empleado e3 = new Empleado();
			e3.setNombre("Luisa");
			e3.setApellidos("apellidos");
			ResponseEntity<Empleado> re = restcontroller.insertarEmpleado_v3(e3);
			
		//THEN:
			assertAll (
					() -> assertEquals (HttpStatus.CREATED, re.getStatusCode(), "Código 201 creado OK"),
					() -> assertEquals (3, service.listar().size(), "Ya hay tres empleados en BBDD")
					);
	}
	
	@Test
	void testInsertarEmpleado_v3_Exception() throws Exception {
		//GIVEN:
			when ( serviceMock.inserta(e1) ).thenThrow (new Exception());
			
		//WHEN:
			ResponseEntity<Empleado> re = restcontrollerMock.insertarEmpleado_v3(e1);
			
		//THEN:
			assertEquals(HttpStatus.NOT_ACCEPTABLE /*INTERNAL_SERVER_ERROR*/, re.getStatusCode(), "Excepción");		
		
	}


}
