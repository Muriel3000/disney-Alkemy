package ar.com.api.disneyalkemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import ar.com.api.disneyalkemy.entities.Genero;
import ar.com.api.disneyalkemy.models.response.GenericResponse;
import ar.com.api.disneyalkemy.models.response.GeneroModel;
import ar.com.api.disneyalkemy.services.GeneroService;
import ar.com.api.disneyalkemy.services.GeneroService.ValidacionGeneroEnum;

@RestController
public class GeneroController {
    
    @Autowired
    private GeneroService service;
    
    @PostMapping("/generos")
    public ResponseEntity<GenericResponse> crearGenero(@RequestBody Genero genero){
        
        GenericResponse r = new GenericResponse();
        
        ValidacionGeneroEnum validacion = service.validacion(genero);

        if(validacion == ValidacionGeneroEnum.OK){
            Genero newGenero = service.crearGenero(genero);
            r.isOk = true;
            r.id = newGenero.getGeneroId();
            r.message = "Genero creado correctamente";
            return ResponseEntity.ok(r);
        } else {
            r.isOk = false;
            r.message = "Error(" + validacion.toString() + ")";
            return ResponseEntity.badRequest().body(r);
        }
    }

    @GetMapping("/generos")
    public ResponseEntity<List<GeneroModel>> traerGeneros(){
        List<Genero> generos = service.traerTodos();
        List<GeneroModel> gResponse = new ArrayList<>();
        for(Genero g : generos){
            GeneroModel generoResponse = GeneroModel.convertirDesde(g);
            gResponse.add(generoResponse);
        }
        return ResponseEntity.ok(gResponse);
    }
}
