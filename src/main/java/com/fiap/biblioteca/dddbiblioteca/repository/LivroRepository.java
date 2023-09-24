package com.fiap.biblioteca.dddbiblioteca.repository;

import com.fiap.biblioteca.dddbiblioteca.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    /*
         Nesta consulta, a subconsulta retorna os IDs dos livros com empréstimos ativos
         (onde a data de devolução é nula ou posterior à data atual).
         A junção LEFT JOIN é usada para combinar todos os livros da tabela "livro" com os empréstimos ativos.
         Em seguida, a cláusula WHERE verifica se o ID do livro não está presente na lista de empréstimos ativos,
         garantindo que todos os livros, incluindo os devolvidos, sejam listados como disponíveis.
     */
    @Query(value = "SELECT livro.id, livro.titulo, livro.autor " +
                   "FROM livro " +
                   "LEFT JOIN ( " +
                   "    SELECT livro_id " +
                   "    FROM emprestimo " +
                   "    WHERE data_devolucao IS NULL OR data_devolucao > CURRENT_DATE " +
                   ") AS emprestimos_ativos ON livro.id = emprestimos_ativos.livro_id " +
                   "WHERE emprestimos_ativos.livro_id IS NULL;", nativeQuery = true)
    List<Livro> listarLivrosDisponiveis();

}