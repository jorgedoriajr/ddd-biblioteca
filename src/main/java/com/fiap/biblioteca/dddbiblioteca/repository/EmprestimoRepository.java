package com.fiap.biblioteca.dddbiblioteca.repository;

import com.fiap.biblioteca.dddbiblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByClienteId(Long clienteId);
    List<Emprestimo> findAllByDataDevolucaoIsNull();

}