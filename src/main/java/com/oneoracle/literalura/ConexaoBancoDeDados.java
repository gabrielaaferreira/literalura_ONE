package com.oneoracle.literalura;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class ConexaoBancoDeDados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER) // Alterado para EAGER
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;

    private int numero_downloads;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumero_downloads() {
        return numero_downloads;
    }

    public void setNumero_downloads(int numero_downloads) {
        this.numero_downloads = numero_downloads;
    }
}
