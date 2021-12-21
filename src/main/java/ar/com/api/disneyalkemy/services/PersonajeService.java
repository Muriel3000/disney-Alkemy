package ar.com.api.disneyalkemy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Listener;
import org.springframework.stereotype.Service;

import ar.com.api.disneyalkemy.entities.Pelicula;
import ar.com.api.disneyalkemy.entities.Personaje;
import ar.com.api.disneyalkemy.repositories.PeliculaRepository;
import ar.com.api.disneyalkemy.repositories.PersonajeRepository;

@Service
public class PersonajeService {
    
    @Autowired
    PersonajeRepository repo;

    @Autowired
    PeliculaRepository peliculaRepo;
    
    public Personaje crearPersonaje(String imagen, String nombre, Integer edad, Integer peso, String historia, List<Pelicula> peliculas){
        Personaje p = new Personaje();
        p.setImagen(imagen);
        p.setNombre(nombre);
        p.setEdad(edad);
        p.setPeso(peso);
        p.setHistoria(historia);
        if(peliculas != null)
            this.agregarPeliculas(p, peliculas);
        repo.save(p);
        return p;
    }

    public ValidacionPersonajeEnum validacion(Personaje p){
        
        if(repo.findByNombre(p.getNombre()) != null){ 
            return ValidacionPersonajeEnum.NOMBRE_DE_PERSONAJE_EXISTENTE;
        }
        if(p.getPeliculas() != null){
            List<Pelicula> listaPeliculas = p.getPeliculas();
            for(Pelicula peli : listaPeliculas){
                if(peliculaRepo.findByPeliculaId(peli.getPeliculaId())==null){ 
                    return ValidacionPersonajeEnum.PELICULA_ASIGNADA_INEXISTENTE;
                }
            }
        }
        return ValidacionPersonajeEnum.OK;
    }

    public void agregarPeliculas(Personaje p, List<Pelicula> peliculas){
        for(Pelicula pelicula : peliculas){
            Pelicula peli = peliculaRepo.findByPeliculaId(pelicula.getPeliculaId());
            p.agregarPelicula(peli);
        }
    }

    public enum ValidacionPersonajeEnum {
    OK, PELICULA_ASIGNADA_INEXISTENTE, NOMBRE_DE_PERSONAJE_EXISTENTE
    }
    
    public List<Personaje> traerTodos(){
        return repo.findAll();   
    }

    public Integer modificarPersonaje(Integer personajeId, Personaje p){
        Personaje personaje = repo.findByPersonajeId(personajeId);
        if(p.getImagen() != null)
            personaje.setImagen(p.getImagen());
        if(p.getNombre() != null)
            personaje.setNombre(p.getNombre());
        if(p.getHistoria() != null)
            personaje.setHistoria(p.getHistoria());
        if(p.getEdad() != null)
            personaje.setEdad(p.getEdad());
        if(p.getPeso() != null)
            personaje.setPeso(p.getPeso());
        if(p.getPeliculas() != null)
            this.agregarPeliculas(personaje, p.getPeliculas());
        repo.save(personaje);
        return personaje.getPersonajeId();
    }

    public void eliminarPersonaje(Integer personajeId){
        Personaje p = repo.findByPersonajeId(personajeId);
        for(Pelicula peli : p.getPeliculas()){
            peli.getPersonajes().remove(p);
            peliculaRepo.save(peli);
        }
        p.getPeliculas().removeAll(p.getPeliculas());
        repo.save(p);
        repo.deleteById(personajeId);
    }

    public Personaje traerPersonaje(Integer personajeId){
        return repo.findByPersonajeId(personajeId);
    }

    public Personaje traerPersonajePorNombre(String nombre){
        return repo.findByNombre(nombre);
    }

    public List<Personaje> traerPersonajesPorEdad(Integer edad){
        return repo.findAllByEdad(edad);
    }

    public List<Personaje> traerPersonajesPorPelicula(Integer peliculaId){
        Pelicula peli = peliculaRepo.findByPeliculaId(peliculaId);
        return peli.getPersonajes();
    }

    public List<Personaje> traerPersonajesPorPeso(Integer peso){
        return repo.findAllByPeso(peso);
    }
}
                                                                                             