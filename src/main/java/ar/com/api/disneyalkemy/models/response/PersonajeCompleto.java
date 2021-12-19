package ar.com.api.disneyalkemy.models.response;

import java.util.ArrayList;
import java.util.List;

import ar.com.api.disneyalkemy.entities.Pelicula;
import ar.com.api.disneyalkemy.entities.Personaje;

public class PersonajeCompleto {

    public Integer personajeId;
    public String imagen;
    public String nombre;
    public Integer edad;
    public Integer peso;
    public String historia;
    public List<PeliculaModel> peliculas;

    public static PersonajeCompleto convertirDesde(Personaje p){
        PersonajeCompleto model = new PersonajeCompleto();
        model.personajeId = p.getPersonajeId();
        model.imagen = p.getImagen();
        model.nombre = p.getNombre();
        model.edad = p.getEdad();
        model.peso = p.getPeso();
        model.historia = p.getHistoria();
        if(p.getPeliculas() != null){
            model.peliculas = new ArrayList<>();
            for(Pelicula peli : p.getPeliculas()){
                PeliculaModel peliModel = PeliculaModel.convertirDesde(peli);
                model.peliculas.add(peliModel);
            }
        }
        return model;
    }

    
}
