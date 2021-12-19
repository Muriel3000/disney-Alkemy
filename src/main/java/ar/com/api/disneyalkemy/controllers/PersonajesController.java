package ar.com.api.disneyalkemy.controllers;

import java.util.*;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.api.disneyalkemy.entities.Personaje;
import ar.com.api.disneyalkemy.models.response.GenericResponse;
import ar.com.api.disneyalkemy.models.response.PersonajeCompleto;
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
    public ResponseEntity<PersonajeCompleto> traerPersonaje(@PathVariable Integer personajeId){
        Personaje p = service.traerPersonaje(personajeId);
        PersonajeCompleto personaje = PersonajeCompleto.convertirDesde(p);
        return ResponseEntity.ok(personaje);
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

    @RequestMapping(value = "characters", method = RequestMethod.GET, params = "name")
    public ResponseEntity<PersonajeCompleto> traerPersonajePorNombre(@RequestParam("name") String nombre){
        Personaje p = service.traerPersonajePorNombre(nombre);
        PersonajeCompleto personaje = PersonajeCompleto.convertirDesde(p);
        return ResponseEntity.ok(personaje);
    }

    @RequestMapping(value = "characters", method = RequestMethod.GET, params = "age")
    public ResponseEntity<List<PersonajeCompleto>> traerPersonajesPorEdad(@RequestParam("age") Integer edad){
        List<Personaje> personajes = service.traerPersonajesPorEdad(edad);
        List<PersonajeCompleto> personajesResponse = new ArrayList<>();
        for(Personaje p : personajes){
            PersonajeCompleto personaje = PersonajeCompleto.convertirDesde(p);
            personajesResponse.add(personaje); 
        }
        return ResponseEntity.ok(personajesResponse);
    }

    @RequestMapping(value = "characters", method = RequestMethod.GET, params = "movies")
    public ResponseEntity<List<PersonajeCompleto>> traerPersonajesPorPelicula(@RequestParam("movies") Integer idMovie){
        List<Personaje> personajes = service.traerPersonajesPorPelicula(idMovie);
        List<PersonajeCompleto> personajesResponse = new ArrayList<>();
        for(Personaje p : personajes){
            PersonajeCompleto personaje = PersonajeCompleto.convertirDesde(p);
            personajesResponse.add(personaje); 
        }
        return ResponseEntity.ok(personajesResponse);
    }

    @RequestMapping(value = "characters", method = RequestMethod.GET, params = "weight")
    public ResponseEntity<List<PersonajeCompleto>> traerPersonajesPorPeso(@RequestParam("weight") Integer peso){
        List<Personaje> personajes = service.traerPersonajesPorPeso(peso);
        List<PersonajeCompleto> personajesResponse = new ArrayList<>();
        for(Personaje p : personajes){
            PersonajeCompleto personaje = PersonajeCompleto.convertirDesde(p);
            personajesResponse.add(personaje); 
        }
        return ResponseEntity.ok(personajesResponse);
    }
}
