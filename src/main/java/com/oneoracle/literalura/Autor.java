package com.oneoracle.literalura;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int ano_nascimento;

    private Integer ano_falecimento; // Use Integer para permitir valores nulos

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno_nascimento() {
        return ano_nascimento;
    }

    public void setAno_nascimento(int ano_nascimento) {
        this.ano_nascimento = ano_nascimento;
    }

    public Integer getAno_falecimento() {
        return ano_falecimento;
    }

    public void setAno_falecimento(Integer ano_falecimento) {
        this.ano_falecimento = ano_falecimento;
    }
}
