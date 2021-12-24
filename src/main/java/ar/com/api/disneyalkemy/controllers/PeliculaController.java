package ar.com.api.disneyalkemy.controllers;

import java.util.*  ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import ar.com.api.disneyalkemy.services.PeliculaService;
import ar.com.api.disneyalkemy.services.GeneroService.ValidacionGeneroEnum;
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
        
        ValidacionPeliculaEnum validacion = service.validacionCreacion(p);

        if(validacion == ValidacionPeliculaEnum.OK){
            Pelicula pelicula = service.crearPelicula(p.getImagen(), p.getTitulo(), p.getCalificacion(), p.getPersonajes(), p.getGenero(), p.getFechaDeCreacion());
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
    public ResponseEntity<?> traerPelicula(@PathVariable Integer peliculaId){
        ValidacionPeliculaEnum validacion = service.validacionPorId(peliculaId);
        if(validacion == ValidacionPeliculaEnum.OK){
            Pelicula p = service.traerPelicula(peliculaId);
            PeliculaCompleta peliResponse = PeliculaCompleta.convertirDesde(p);
            return ResponseEntity.ok(peliResponse);
        } else {
            GenericResponse response = new GenericResponse();
            response.isOk = false;
            response.id = peliculaId;
            response.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.ok(response);
        }
        
    }

    @PutMapping("/movies/{peliculaId}")
    public ResponseEntity<GenericResponse> modificarPelicula(@PathVariable Integer peliculaId, @RequestBody Pelicula p){
        
        GenericResponse response = new GenericResponse();
        ValidacionPeliculaEnum validacion = service.validacionModificacion(p);

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
        
        GenericResponse response = new GenericResponse();
        ValidacionPeliculaEnum validacion = service.validacionPorId(peliculaId);
        
        if(validacion == ValidacionPeliculaEnum.OK){
            service.eliminarPelicula(peliculaId);
            response.isOk = true;
            response.message = "La pelicula ha sido eliminada";
            response.id = peliculaId;
            return ResponseEntity.ok(response);
        } else {
            response.isOk = false;
            response.id = peliculaId;
            response.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.ok(response);
        }
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET, params = "name")
    public ResponseEntity<PeliculaCompleta> traerPeliculaPorNombre(@RequestParam("name") String titulo){
        Pelicula p = service.traerPeliculaPorTitulo(titulo);
        PeliculaCompleta peli = PeliculaCompleta.convertirDesde(p);
        return ResponseEntity.ok(peli);
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET, params = "genre")
    public ResponseEntity<List<PeliculaCompleta>> traerPeliculasPorGenero(@RequestParam("genre") Integer generoId){
        List<Pelicula> peliculas = service.traerPeliculasPorGenero(generoId);
        List<PeliculaCompleta> pelisResponse = new ArrayList<>();
        for(Pelicula p : peliculas){
            PeliculaCompleta peli = PeliculaCompleta.convertirDesde(p);
            pelisResponse.add(peli);
        }
        return ResponseEntity.ok(pelisResponse);
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET, params = "order")
    public ResponseEntity<List<PeliculaCompleta>> traerPeliculasEnOrden(@RequestParam("order") String orden){
        List<Pelicula> peliculas = service.traerPeliculasEnOrden(orden);
        List<PeliculaCompleta> pelisResponse = new ArrayList<>();
        for(Pelicula p : peliculas){
            PeliculaCompleta peli = PeliculaCompleta.convertirDesde(p);
            pelisResponse.add(peli);
        }
        return ResponseEntity.ok(pelisResponse);
    } 


}
