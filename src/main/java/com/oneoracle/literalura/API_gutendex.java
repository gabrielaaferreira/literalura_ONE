package com.oneoracle.literalura;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class API_gutendex {

    private static final Logger logger = Logger.getLogger(API_gutendex.class.getName());

    @Autowired
    private BancoDeLivros bancoDeLivros; // Repositório para livros

    @Autowired
    private AutorRepository autorRepository; // Repositório para autores

    public void buscarLivro(String title) {
        String API_BASE_URL = "https://gutendex.com/books/?search=" + title.replace(" ", "%20");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();

                // Use Gson para converter o JSON em objetos Java
                Gson gson = new Gson();
                Response responseObject = gson.fromJson(json, Response.class);

                if (responseObject != null && !responseObject.getResults().isEmpty()) {
                    Response.BookResult bookResult = responseObject.getResults().get(0);

                    String bookTitle = bookResult.getTitle();
                    String[] bookLanguages = bookResult.getLanguages();
                    int downloadCount = bookResult.getDownload_count();

                    // Verifica se o livro já existe pelo título
                    Optional<ConexaoBancoDeDados> livroExistente = bancoDeLivros.findByTitulo(bookTitle);
                    if (livroExistente.isPresent()) {
                        System.out.println("Livro já cadastrado com título: " + bookTitle);
                        return; // Sai do método sem salvar o livro novamente
                    }

                    // Assume que há apenas um autor no primeiro resultado
                    List<Response.Person> authors = bookResult.getAuthors();
                    if (authors != null && !authors.isEmpty()) {
                        Response.Person author = authors.get(0);
                        String authorName = author.getName();
                        int birthYear = author.getBirth_year();
                        int deathYear = author.getDeath_year();

                        // Verifica se o autor já existe pelo nome
                        Optional<Autor> autorExistente = autorRepository.findByNome(authorName);
                        if (autorExistente.isPresent()) {
                            // Autor já existe, não precisa salvar novamente
                            System.out.println("Autor já cadastrado com nome: " + authorName);
                        } else {
                            // Autor não existe, cria um novo
                            Autor novoAutor = new Autor();
                            novoAutor.setNome(authorName);
                            novoAutor.setAno_nascimento(birthYear);
                            novoAutor.setAno_falecimento(deathYear);
                            autorRepository.save(novoAutor); // Salva o novo autor no banco
                            System.out.println("Novo autor cadastrado com nome: " + authorName);
                        }

                        // Cria uma instância da entidade ConexaoBancoDeDados
                        ConexaoBancoDeDados livro = new ConexaoBancoDeDados();
                        livro.setTitulo(bookTitle);
                        livro.setNumero_downloads(downloadCount);

                        // Busca o autor novamente para garantir que está presente no banco
                        autorExistente = autorRepository.findByNome(authorName);
                        if (autorExistente.isPresent()) {
                            livro.setAutor(autorExistente.get()); // Associa o autor ao livro
                        } else {
                            // Tratar o caso de erro se o autor não estiver presente (não deve ocorrer se o fluxo for correto)
                            throw new RuntimeException("Erro ao buscar autor pelo nome: " + authorName);
                        }

                        // Define o idioma do livro
                        if (bookLanguages.length > 0) {
                            livro.setIdioma(bookLanguages[0]); // Considera apenas o primeiro idioma
                        }

                        // Salva o livro no banco de dados usando o repositório JPA
                        bancoDeLivros.save(livro);

                        System.out.println("Livro cadastrado com sucesso!");
                    } else {
                        System.err.println("Nenhum autor encontrado para o livro: " + bookTitle);
                    }
                } else {
                    System.err.println("Nenhum resultado encontrado para a pesquisa: " + title);
                }
            } else {
                System.err.println("Erro na resposta da API: Código " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Erro ao fazer requisição HTTP", e);
        }
    }
}