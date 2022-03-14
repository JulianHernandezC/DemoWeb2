package com.nttdata.demoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.demoweb.repository.entity.Usuario;

public interface UsuarioRepoJPA extends JpaRepository<Usuario, String> {

}
