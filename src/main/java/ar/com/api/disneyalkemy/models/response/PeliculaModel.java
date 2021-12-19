package ar.com.api.disneyalkemy.models.response;

import ar.com.api.disneyalkemy.entities.Pelicula;

public class PeliculaModel {

    public Integer peliculaId;
    public String titulo;

    public static PeliculaModel convertirDesde(Pelicula p){
        PeliculaModel model = new PeliculaModel();
        model.peliculaId = p.getPeliculaId();
        model.titulo = p.getTitulo();
        return model;
    }
}
