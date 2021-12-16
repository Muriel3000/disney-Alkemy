package ar.com.api.disneyalkemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.disneyalkemy.entities.Genero;

@Repository
public interface GeneroRepository extends JpaRepository <Genero, Integer>{
    public Genero findByNombre(String nombre);
    public Genero findByGeneroId(Integer id);
}
