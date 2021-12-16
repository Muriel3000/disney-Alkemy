package ar.com.api.disneyalkemy.controllers;

import java.util.*;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.api.disneyalkemy.entities.Personaje;
import ar.com.api.disneyalkemy.models.response.GenericResponse;
import ar.com.api.disneyalkemy.models.response.PersonajeModel;
import ar.com.api.disneyalkemy.services.PersonajeService;
import ar.com.api.disneyalkemy.services.PersonajeService.ValidacionPersonajeEnum;

@RestController
public class PersonajesController {
    
    @Autowired
    PersonajeService service;

    @PostMapping("/characters")
    public ResponseEntity<GenericResponse> crearPersonaje(@RequestBody Personaje p){
        
        GenericResponse response = new GenericResponse();
        
        ValidacionPersonajeEnum validacion = service.validacion(p);
        
        if(validacion == ValidacionPersonajeEnum.OK){
            Personaje personaje = service.crearPersonaje(p.getImagen(), p.getNombre(), p.getEdad(), 
            p.getPeso(), p.getHistoria(), p.getPeliculas());
            response.isOk = true;
            response.message = "Personaje creado";
            response.id = personaje.getPersonajeId();
            return ResponseEntity.ok(response);
        } else {
            response.isOk = false;
            response.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.badRequest().body(response);
        }  
    }

    @GetMapping("/characters")
    public ResponseEntity<List<PersonajeModel>> traerPersonajesNombreEImagen(){
        List<PersonajeModel> lista = new ArrayList<>();
        for(Personaje p : service.traerTodos()){
            PersonajeModel personaje = PersonajeModel.convertirDesde(p);
            lista.add(personaje);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/characters/details/{personajeId}")
    public ResponseEntity<Personaje> traerPersonaje(@PathVariable Integer personajeId){
        Personaje p = service.traerPersonaje(personajeId);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/characters/all")
    public ResponseEntity<List<Personaje>> traerPersonajes(){
        return ResponseEntity.ok(service.traerTodos());
    }

    @PutMapping("/characters/{personajeId}")
    public ResponseEntity<GenericResponse> modificarPersonaje(@PathVariable Integer personajeId, @RequestBody Personaje p){
        
        GenericResponse response = new GenericResponse();
        ValidacionPersonajeEnum validacion = service.validacion(p);

        if(validacion == ValidacionPersonajeEnum.OK){
            Integer personajeModificadoId = service.modificarPersonaje(personajeId, p);          
            response.isOk = true;
            response.message = "El personaje ha sido modificado";
            response.id = personajeModificadoId;
            return ResponseEntity.ok(response);
        } else {
            response.isOk = false;
            response.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.badRequest().body(response);
        }  
    }

    @DeleteMapping("/characters/{personajeId}")
    public ResponseEntity<GenericResponse> eliminarPersonaje(@PathVariable Integer personajeId){
        service.eliminarPersonaje(personajeId);
        GenericResponse response = new GenericResponse();
        response.isOk = true;
        response.message = "El personaje ha sido eliminado";
        response.id = personajeId;
        return ResponseEntity.ok(response);
    }

    @GetMapping("/characters?name={nombre}")
    public ResponseEntity<Personaje> traerPersonajePorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(service.traerPersonajePorNombre(nombre));
    }

    @GetMapping("/characters?age={edad}")
    public ResponseEntity<List<Personaje>> traerPersonajesPorEdad(@PathVariable Integer edad){
        return ResponseEntity.ok(service.traerPersonajesPorEdad(edad));
    }

    @GetMapping("/characters?movies={idMovie}")
    public ResponseEntity<List<Personaje>> traerPersonajesPorPelicula(@PathVariable Integer idMovie){
        return ResponseEntity.ok(service.traerPersonajesPorPelicula(idMovie));
    }
}
