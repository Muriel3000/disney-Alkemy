package ar.com.api.disneyalkemy.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.api.disneyalkemy.entities.Genero;
import ar.com.api.disneyalkemy.entities.Pelicula;
import ar.com.api.disneyalkemy.entities.Personaje;
import ar.com.api.disneyalkemy.repositories.GeneroRepository;
import ar.com.api.disneyalkemy.repositories.PeliculaRepository;
import ar.com.api.disneyalkemy.repositories.PersonajeRepository;

@Service
public class PeliculaService {
   
    @Autowired
    private PeliculaRepository repo;

    @Autowired
    private GeneroRepository generoRepo;

    @Autowired
    private PersonajeRepository personajeRepo;

    public Pelicula crearPelicula(String imagen, String titulo, Integer calificacion, List<Personaje> personajes, Genero genero){
        Pelicula p = new Pelicula();
        p.setImagen(imagen);
        p.setTitulo(titulo);
        p.setGenero(generoRepo.findByGeneroId(genero.getGeneroId()));
        if(personajes != null)
            this.agregarPersonajes(p, personajes);
        p.setFechaDeCreacion(new Date());
        p.setCalificacion(calificacion);
        repo.save(p);
        return p;
    }

    public ValidacionPeliculaEnum validacion(Pelicula p){
        if(p.getGenero() == null || generoRepo.findByGeneroId(p.getGenero().getGeneroId()) == null)
            return ValidacionPeliculaEnum.GENERO_INEXISTENTE;
        if(p.getPersonajes() != null){
            for(Personaje personaje : p.getPersonajes()){
                if(personajeRepo.findByPersonajeId(personaje.getPersonajeId()) == null)
                    return ValidacionPeliculaEnum.PERSONAJE_ASIGNADO_INEXISTENTE;
            }
        }
        if(verificarCalificacion(p.getCalificacion()) == false)
            return ValidacionPeliculaEnum.CALIFICACION_INVALIDA;
        return ValidacionPeliculaEnum.OK;
    }

    public enum ValidacionPeliculaEnum{
        OK, GENERO_INEXISTENTE, PERSONAJE_ASIGNADO_INEXISTENTE, CALIFICACION_INVALIDA
    }

    public void agregarPersonajes(Pelicula peli, List<Personaje> personajes){
        for(Personaje personaje : personajes){
            Personaje  p = personajeRepo.findByPersonajeId(personaje.getPersonajeId());
            p.agregarPelicula(peli);
        }
    }
   
    public boolean verificarCalificacion(Integer cal){
        if(cal != 1 && cal != 2 && cal != 3 && cal != 4 && cal != 5){
            return false;
        } else return true;
    }

    public List<Pelicula> traerTodos(){
        return repo.findAll();
    }

    public Pelicula traerPelicula(Integer peliId){
        return repo.findByPeliculaId(peliId);
    }
}
