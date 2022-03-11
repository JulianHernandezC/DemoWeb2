package com.nttdata.demoweb.service;

import java.util.List;

import com.nttdata.demoweb.repository.entity.Empleado;

public interface EmpleadoService {
	void registrar(String name);
	List<Empleado> listar();
	List<Empleado> listarFiltroNombre (String cad);
	List<Empleado> listarConJPA (Integer pId, String contiene);
	List<Empleado> listarFiltroNombreEs (String cad);
	
}
