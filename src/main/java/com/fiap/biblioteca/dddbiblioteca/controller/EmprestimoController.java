package com.fiap.biblioteca.dddbiblioteca.controller;

import com.fiap.biblioteca.dddbiblioteca.service.EmprestimoApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EmprestimoController {
    private final EmprestimoApplicationService emprestimoAppService;

    @Autowired
    public EmprestimoController(EmprestimoApplicationService emprestimoAppService) {
        this.emprestimoAppService = emprestimoAppService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("livros", emprestimoAppService.listarLivrosDisponiveis());
        model.addAttribute("clientes", emprestimoAppService.listarClientes());
        model.addAttribute("emprestimos", emprestimoAppService.listarEmprestimosAtivos());
        return "index";
    }

    @GetMapping("/cliente")
    public String escolherCliente(Model model) {
        model.addAttribute("clientes", emprestimoAppService.listarClientes());
        return "escolher-cliente";
    }

    @GetMapping("/escolher-livro/{clienteId}")
    public String escolherLivro(@PathVariable Long clienteId, Model model) {
        model.addAttribute("cliente", emprestimoAppService.buscarCliente(clienteId));
        model.addAttribute("livros", emprestimoAppService.listarLivrosDisponiveis());
        return "escolher-livro";
    }

    @GetMapping("/realizar-emprestimo/{clienteId}/{livroId}")
    public String realizarEmprestimo(@PathVariable Long clienteId,
                                     @PathVariable Long livroId) {
        emprestimoAppService.realizarEmprestimo(clienteId, livroId);
        return "redirect:/index";
    }

    @GetMapping("/ver-emprestimos/{clienteId}")
    public String verEmprestimos(@PathVariable Long clienteId, Model model) {
        model.addAttribute("cliente", emprestimoAppService.buscarCliente(clienteId));
        model.addAttribute("emprestimos", emprestimoAppService.listarEmprestimosCliente(clienteId));
        return "ver-emprestimos";
    }

    @GetMapping("/escolher-devolucao")
    public String escolherDevolucao(Model model) {
        model.addAttribute("emprestimos", emprestimoAppService.listarEmprestimosAtivos());
        return "escolher-devolucao";
    }

    @GetMapping("/realizar-devolucao/{emprestimoId}")
    public String realizarDevolucao(@PathVariable Long emprestimoId) {
        emprestimoAppService.realizarDevolucao(emprestimoId);
        return "redirect:/index";
    }
}