package ar.com.api.disneyalkemy.controllers;

import java.util.*  ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import ar.com.api.disneyalkemy.services.PeliculaService;
import ar.com.api.disneyalkemy.services.PeliculaService.ValidacionPeliculaEnum;
import ar.com.api.disneyalkemy.models.response.GenericResponse;
import ar.com.api.disneyalkemy.models.response.PeliculaCompleta;
import ar.com.api.disneyalkemy.models.response.PeliculaConFecha;
import ar.com.api.disneyalkemy.entities.Pelicula;

@RestController
public class PeliculaController {
    
    @Autowired
    private PeliculaService service;

    @PostMapping("/movies")
    public ResponseEntity<GenericResponse> crearPelicula(@RequestBody Pelicula p){
        
        GenericResponse r = new GenericResponse();
        
        ValidacionPeliculaEnum validacion = service.validacion(p);

        if(validacion == ValidacionPeliculaEnum.OK){
            Pelicula pelicula = service.crearPelicula(p.getImagen(), p.getTitulo(), p.getCalificacion(), p.getPersonajes(), p.getGenero());
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

    @GetMapping("/movies")
    public ResponseEntity<List<PeliculaConFecha>> traerPeliculas(){
        List<Pelicula> pelis = service.traerTodos();
        List<PeliculaConFecha> response = new ArrayList<>();
        for(Pelicula p : pelis){
            PeliculaConFecha peliResponse = PeliculaConFecha.convertirDesde(p);
            response.add(peliResponse);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movies/{peliculaId}")
    public ResponseEntity<PeliculaCompleta> traerPelicula(@PathVariable Integer peliculaId){
        Pelicula p = service.traerPelicula(peliculaId);
        PeliculaCompleta peliResponse = PeliculaCompleta.convertirDesde(p);
        return ResponseEntity.ok(peliResponse);
    }

    @PutMapping("/movies/{peliculaId}")
    public ResponseEntity<GenericResponse> modificarPelicula(@PathVariable Integer peliculaId, @RequestBody Pelicula p){
        
        GenericResponse response = new GenericResponse();
        ValidacionPeliculaEnum validacion = service.validacion(p);

        if(validacion == ValidacionPeliculaEnum.OK){
            Integer peliculaModificadaId = service.modificarPelicula(peliculaId, p);          
            response.isOk = true;
            response.message = "La pelicula ha sido modificada";
            response.id = peliculaModificadaId;
            return ResponseEntity.ok(response);
        } else {
            response.isOk = false;
            response.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.badRequest().body(response);
        }  
    }

    @DeleteMapping("/movies/{peliculaId}")
    public ResponseEntity<GenericResponse> eliminarPelicula(@PathVariable Integer peliculaId){
        service.eliminarPelicula(peliculaId);
        GenericResponse response = new GenericResponse();
        response.isOk = true;
        response.message = "La elicula ha sido eliminada";
        response.id = peliculaId;
        return ResponseEntity.ok(response);
    }


}
