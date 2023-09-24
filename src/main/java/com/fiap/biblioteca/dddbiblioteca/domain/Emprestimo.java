package com.fiap.biblioteca.dddbiblioteca.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    @ManyToOne
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    private LocalDate dataEmprestimo;

    @Temporal(TemporalType.DATE)
    private LocalDate dataDevolucao;
}