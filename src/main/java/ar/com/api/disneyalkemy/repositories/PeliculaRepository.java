package ar.com.api.disneyalkemy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.disneyalkemy.entities.Genero;
import ar.com.api.disneyalkemy.entities.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository <Pelicula, Integer>{
    public Pelicula findByPeliculaId(Integer id);
    public Pelicula findByTitulo(String titulo);
    public List<Pelicula> findAllByGenero(Genero genero);
}
