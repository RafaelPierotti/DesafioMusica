package DesafioMusica.Musica.Principal;

import DesafioMusica.Musica.Models.Artista;
import DesafioMusica.Musica.Models.Musica;
import DesafioMusica.Musica.Models.TipoArtista;
import DesafioMusica.Musica.Repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private ArtistaRepository repository;
    private Scanner scanner = new Scanner(System.in);

    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }


    public void exibeMenu(){
        var option = 0;

        var menu = """
            1 - Cadastrar artistas
            2 - Cadastrar músicas
            3 - Listar músicas 
            4 - Buscar músicas pelo artistas
            
            9 - Sair
            """;

        while (option != 9){
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusica();
                    break;
                case 4:
                    buscarMusicaArtista();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!!");
            }
        }

    }

    private void cadastrarArtista(){
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do artista: ");
            var nome = scanner.nextLine();

            System.out.println("Informe o tipo desse artista (solo / dupla / banda)");
            var tipoGenero = scanner.nextLine();

            TipoArtista tipoArtista = TipoArtista.valueOf(tipoGenero.toUpperCase());

            Artista artista = new Artista(nome, tipoArtista);

            repository.save(artista);

            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = scanner.nextLine();
        }
    }

    private void cadastrarMusica(){
        System.out.println("Cadastrar a música de qual artista? ");
        var nome = scanner.nextLine();
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);

        if (artista.isPresent()){
            System.out.println("Informe o título da música: ");
            var nomeMusica = scanner.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());//setar o artista dessa musica
            artista.get().getMusicas().add(musica);
            repository.save(artista.get());
        } else {
            System.out.println("Artista não encontrado!!");
        }
    }

    private void listarMusica(){
        List<Artista> artistas = repository.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscarMusicaArtista(){
        System.out.println("Qual artista você deseja buscar o artista? ");
        var nome = scanner.nextLine();
        List<Musica> musicas = repository.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }
}
