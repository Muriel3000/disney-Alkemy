package ar.com.api.disneyalkemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.disneyalkemy.entities.Personaje;

import java.util.*;

@Repository
public interface PersonajeRepository extends JpaRepository <Personaje, Integer>{
    public Personaje findByNombre(String nombre);
    public Personaje findByPersonajeId(Integer id);
    public List<Personaje> findAllByEdad(Integer edad);
}
