package ar.com.api.disneyalkemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import ar.com.api.disneyalkemy.entities.Genero;
import ar.com.api.disneyalkemy.entities.Pelicula;
import ar.com.api.disneyalkemy.repositories.GeneroRepository;
import ar.com.api.disneyalkemy.repositories.PeliculaRepository;

@Service
public class GeneroService {
    
    @Autowired
    private GeneroRepository repo;

    @Autowired
    private PeliculaRepository peliculaRepo;

    public Genero crearGenero(Genero genero){
        if(genero.getPeliculas() != null)
            this.agregarPeliculas(genero);
        repo.save(genero);
        return genero;
    }

    public ValidacionGeneroEnum validacion(Genero g){ 
        if(repo.findByNombre(g.getNombre()) != null)
            return ValidacionGeneroEnum.GENERO_EXISTENTE;
        if(g.getPeliculas() != null){
            for(Pelicula p : g.getPeliculas()){
                if(peliculaRepo.findByPeliculaId(p.getPeliculaId()) == null)
                    return ValidacionGeneroEnum.PELICULA_ASIGNADA_INEXISTENTE;
            }
        }
        return ValidacionGeneroEnum.OK;
    }

    public enum ValidacionGeneroEnum{
        OK, PELICULA_ASIGNADA_INEXISTENTE, GENERO_EXISTENTE;
    }

    public void agregarPeliculas(Genero g){
        List<Pelicula> lista = g.getPeliculas();
        for(Pelicula pelicula : lista){
            Pelicula peli = peliculaRepo.findByPeliculaId(pelicula.getPeliculaId());
            g.agregarPelicula(peli);
        }
    }

    public List<Genero> traerTodos(){
        return repo.findAll();
    }



}
