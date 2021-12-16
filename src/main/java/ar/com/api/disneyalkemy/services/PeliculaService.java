package ar.com.api.disneyalkemy.services;

import org.springframework.stereotype.Service;

@Service
public class PeliculaService {
   
    public Pelicula crearPelicula(Pelicula p){
        
    }
   
    public Integer verificarCalificacion(Integer cal){
        if(cal != 1 || cal != 2 || cal != 3 || cal != 4 || cal != 5){
            return null;
        }
        else return cal;
    }
}
