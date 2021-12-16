package ar.com.api.disneyalkemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.disneyalkemy.entities.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository <Pelicula, Integer>{
    public Pelicula findByPeliculaId(Integer id);
}
