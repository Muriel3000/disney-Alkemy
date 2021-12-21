package ar.com.api.disneyalkemy.models.response;

import ar.com.api.disneyalkemy.entities.Genero;

public class GeneroModel {
    public Integer generoId;
    public String nombre;

    public static GeneroModel convertirDesde(Genero g){
        GeneroModel gm = new GeneroModel();
        gm.generoId = g.getGeneroId();
        gm.nombre = g.getNombre();
        return gm;
    }
    
}
