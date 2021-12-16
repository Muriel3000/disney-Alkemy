package ar.com.api.disneyalkemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import ar.com.api.disneyalkemy.services.PeliculaService;
import ar.com.api.disneyalkemy.models.response.GenericResponse;
import ar.com.api.disneyalkemy.entities.Pelicula;

@RestController
public class PeliculaController {
    
    @Autowired
    private PeliculaService service;

    @PostMapping("/peliculas")
    public ResponseEntity<GenericResponse> crearPelicula(@RequestBody Pelicula p){
        
        GenericResponse r = new GenericResponse();
        
        ValidacionPeliculaEnum validacion = service.validacion(p);

        if(validacion == ValidacionPeliculaEnum.OK){
            Pelicula pelicula = service.crearPelicual(p);
            r.isOk = true;
            r.id = pelicula.getPeliculaId();
            r.message = "Pelicula creada correctamente";
            return ResponseEntity.ok(r);
        } else {
            r.isOk = false;
            r.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.badRequest().body(r);
        }
    }


}
