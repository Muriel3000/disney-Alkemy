package ar.com.api.disneyalkemy.models.response;

import ar.com.api.disneyalkemy.entities.Personaje;

public class PersonajeModel {
    public String imagen;
    public String nombre;

    public static PersonajeModel convertirDesde(Personaje p){
        PersonajeModel pm = new PersonajeModel();
        pm.imagen = p.getImagen();
        pm.nombre = p.getNombre();
        return pm;
    }
}
