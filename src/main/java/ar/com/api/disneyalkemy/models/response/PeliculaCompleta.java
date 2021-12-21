package ar.com.api.disneyalkemy.models.response;

import java.util.*;

import ar.com.api.disneyalkemy.entities.Pelicula;
import ar.com.api.disneyalkemy.entities.Personaje;

public class PeliculaCompleta {
    public Integer peliculaId;
    public String imagen;
    public String titulo;
    public Integer calificacion;
    public Date fechaDeCreacion;
    public GeneroModel genero;
    public List<PersonajeModel> personajes;

    public static PeliculaCompleta convertirDesde(Pelicula p){
        PeliculaCompleta peli = new PeliculaCompleta();
        peli.peliculaId = p.getPeliculaId();
        peli.imagen = p.getImagen();
        peli.titulo = p.getTitulo();
        peli.calificacion = p.getCalificacion();
        peli.fechaDeCreacion = p.getFechaDeCreacion();
        if(p.getGenero() != null){
            GeneroModel gm = GeneroModel.convertirDesde(p.getGenero());
            peli.genero = gm;
        }
        if(p.getPersonajes() != null){
            peli.personajes = new ArrayList<>();
            for(Personaje personaje : p.getPersonajes()){
                PersonajeModel model = PersonajeModel.convertirDesde(personaje);
                peli.personajes.add(model);
            }
        }
        return peli;
    }

}
