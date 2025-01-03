package DesafioMusica.Musica.Models;

public enum TipoArtista {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private final String tipo;

    TipoArtista(String tipo){
        this.tipo = tipo;
    }

}
