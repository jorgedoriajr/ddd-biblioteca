package com.fiap.biblioteca.dddbiblioteca.repository;

import com.fiap.biblioteca.dddbiblioteca.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}