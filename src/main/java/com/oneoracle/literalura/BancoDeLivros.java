package com.oneoracle.literalura;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoDeLivros extends JpaRepository<ConexaoBancoDeDados, Long> {

    Optional<ConexaoBancoDeDados> findByTitulo(String titulo);

    List<ConexaoBancoDeDados> findByIdioma(String idioma);

    @Query("SELECT l FROM ConexaoBancoDeDados l WHERE l.autor.nome = :nome")
    Optional<ConexaoBancoDeDados> findByNome(String nome);

    List<ConexaoBancoDeDados> findByAutor_Nome(String nomeAutor);

    @Query("SELECT l FROM ConexaoBancoDeDados l JOIN l.autor a WHERE a.ano_nascimento < :ano AND (a.ano_falecimento IS NULL OR a.ano_falecimento > :ano)")
    List<ConexaoBancoDeDados> findLivrosComAutoresVivosNoAno(int ano);

    @Query("SELECT l FROM ConexaoBancoDeDados l JOIN l.autor a WHERE a.ano_nascimento < :anoNascimento AND (a.ano_falecimento IS NULL OR a.ano_falecimento > :anoFalecimento)")
    List<ConexaoBancoDeDados> findByAnoNascimentoAutorAntesEAnoFalecimentoAutorDepois(int anoNascimento, int anoFalecimento);

    @Query("SELECT DISTINCT l.autor.nome FROM ConexaoBancoDeDados l")
    List<String> findAutoresDistintos();
}
