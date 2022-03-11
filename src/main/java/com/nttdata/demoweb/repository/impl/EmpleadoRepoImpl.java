package com.nttdata.demoweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.nttdata.demoweb.repository.EmpleadoRepo;
import com.nttdata.demoweb.repository.entity.Empleado;

//@Repository
public class EmpleadoRepoImpl implements EmpleadoRepo {
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(EmpleadoRepoImpl.class);
	
	@PersistenceContext
	EntityManager entityManager;
	
	//@Override
    public List<Empleado> listarCuyoNombreContiene(String texto_nombre) {
        Query query = entityManager.createNativeQuery("SELECT * FROM empleado " +
                "WHERE nombre LIKE ?", Empleado.class);
        query.setParameter(1, "%" + texto_nombre + "%");
        return query.getResultList();
	}
	
	
	@Override
	public void registrar (String nombre) {
		LOG.info("Se ha saludado al empleado: "+nombre);
	}
	
}
