package com.fiap.biblioteca.dddbiblioteca.service;

import com.fiap.biblioteca.dddbiblioteca.domain.Cliente;
import com.fiap.biblioteca.dddbiblioteca.domain.Emprestimo;
import com.fiap.biblioteca.dddbiblioteca.domain.Livro;
import com.fiap.biblioteca.dddbiblioteca.repository.ClienteRepository;
import com.fiap.biblioteca.dddbiblioteca.repository.EmprestimoRepository;
import com.fiap.biblioteca.dddbiblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmprestimoApplicationService {
    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    @Autowired
    public EmprestimoApplicationService(ClienteRepository clienteRepository,
                                        LivroRepository livroRepository,
                                        EmprestimoRepository emprestimoRepository) {
        this.clienteRepository = clienteRepository;
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    // Aqui, implementei a lógica para listar livros disponíveis
    public List<Livro> listarLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = livroRepository.listarLivrosDisponiveis();
        return livrosDisponiveis;
    }

    // Aqui, implemente a lógica para listar empréstimos de um cliente
    public List<Emprestimo> listarEmprestimosCliente(Long clienteId) {
        return emprestimoRepository.findByClienteId(clienteId);
    }

    // Aqui, implemente a lógica para listar empréstimos ativos
    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.findAllByDataDevolucaoIsNull();
    }

    @Transactional
    public void realizarEmprestimo(Long clienteId, Long livroId) {
        Cliente cliente = buscarCliente(clienteId);
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCliente(cliente);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        emprestimoRepository.save(emprestimo); // Salva o empréstimo no repositório
    }

    @Transactional
    public void realizarDevolucao(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimoRepository.save(emprestimo); // Atualiza o empréstimo no repositório
    }
}