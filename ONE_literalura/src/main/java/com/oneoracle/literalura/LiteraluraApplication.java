package com.oneoracle.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private API_gutendex api_gutendex;

    @Autowired
    private BancoDeLivros bancoDeLivros;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int sair = 0;
        int userNumber;
        Scanner userInput = new Scanner(System.in);

        while (sair == 0) {
            System.out.println("---------------------------");
            System.out.println("Escolha o número da sua opção:");
            System.out.println("1 - Buscar livro pelo título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos em um determinado ano");
            System.out.println("5 - Listar livros em um determinado idioma");
            System.out.println("0 - Sair");
            System.out.println("---------------------------");
            System.out.println();

            try {
                userNumber = Integer.parseInt(userInput.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
                continue;
            }

            System.out.println();

            switch (userNumber) {
                case 1:
                    System.out.println("Digite o título do livro:");
                    String title = userInput.nextLine();
                    api_gutendex.buscarLivro(title);
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmAno(userInput);
                    break;
                case 5:
                    listarLivrosPorIdioma(userInput);
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    sair = 1;
                    break;
                default:
                    System.out.println("Digite um número válido");
                    break;
            }
        }

        userInput.close();
    }

    private void listarLivrosRegistrados() {
        List<ConexaoBancoDeDados> livros = bancoDeLivros.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("----------- Livros Registrados -----------");
            for (ConexaoBancoDeDados livro : livros) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor().getNome());
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Número de Downloads: " + livro.getNumero_downloads());
                System.out.println("-----------------------------------------");
            }
        }
        System.out.println();
    }

    private void listarAutoresRegistrados() {
        List<String> autores = bancoDeLivros.findAutoresDistintos();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("----------- Autores Registrados -----------");
            for (String autor : autores) {
                System.out.println(autor);
            }
            System.out.println("------------------------------------------");
        }
        System.out.println();
    }

    private void listarAutoresVivosEmAno(Scanner userInput) {
        System.out.println("Digite o ano para listar autores vivos:");
        int ano = Integer.parseInt(userInput.nextLine());

        List<ConexaoBancoDeDados> autores = bancoDeLivros.findLivrosComAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo em " + ano + ".");
        } else {
            System.out.println("----------- Autores Vivos em " + ano + " -----------");
            for (ConexaoBancoDeDados livro : autores) {
                System.out.println("Autor: " + livro.getAutor().getNome());
            }
            System.out.println("-------------------------------------------");
        }
        System.out.println();
    }

    private void listarLivrosPorIdioma(Scanner userInput) {
        System.out.println("Digite o idioma para listar os livros:");
        String idioma = userInput.nextLine();

        List<ConexaoBancoDeDados> livros = bancoDeLivros.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma " + idioma + ".");
        } else {
            System.out.println("----------- Livros em " + idioma + " -----------");
            for (ConexaoBancoDeDados livro : livros) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor().getNome());
                System.out.println("Número de Downloads: " + livro.getNumero_downloads());
                System.out.println("----------------------------------------");
            }
            System.out.println("-----------------------------------------");
        }
        System.out.println();
    }
}
