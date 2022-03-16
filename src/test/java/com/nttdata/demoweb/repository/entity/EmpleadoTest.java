package com.nttdata.demoweb.repository.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmpleadoTest {

	@Test
	void test() {
		Empleado e1 = new Empleado();
		
		e1.setId(1);
		assertEquals(1, e1.getId(), "Mismo id");
		
		String nombre="Nombre Prueba";
		e1.setNombre(nombre);
		assertEquals(nombre, e1.getNombre(), "Mismo error");
		
		String apellidos="Apellidos Prueba";
		e1.setApellidos(apellidos);
		assertEquals(apellidos, e1.getApellidos(), "Mismos apellidos");
		
		Empleado e2 = new Empleado();
		e2.setId(1);
		e2.setNombre(nombre+"222222");
		e2.setApellidos(apellidos);
		assertEquals(e1, e2, "Mismo empleado");
		
		assertEquals(e1.hashCode(), e2.hashCode(), "Mismo hash code");
		
		assertEquals(e1, e1, "Mismo objeto");
		
		assertNotEquals(e1, nombre, "Distinto objeto");
		
		e1.setId(null);
		assertNull(e1.getId(), "id de e1 is null");
		assertNotEquals(e1, e2, "e1 a null y e2 no");
		
	}

}
