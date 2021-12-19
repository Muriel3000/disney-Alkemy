package ar.com.api.disneyalkemy.models.response;

import java.util.Date;

import ar.com.api.disneyalkemy.entities.Pelicula;

public class PeliculaConFecha {
    public String imagen;
    public String titulo;
    public Date fechaDeCreacion;

    public static PeliculaConFecha convertirDesde(Pelicula p){
        PeliculaConFecha peli = new PeliculaConFecha();
        peli.imagen = p.getImagen();
        peli.titulo = p.getTitulo();
        peli.fechaDeCreacion = p.getFechaDeCreacion();
        return peli;
    }
}
