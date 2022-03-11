package com.nttdata.demoweb.repository;

import java.util.List;

import com.nttdata.demoweb.repository.entity.Empleado;

public interface EmpleadoRepo {
	public void registrar (String nombre);
	List<Empleado> listarCuyoNombreContiene(String texto_nombre);
}
