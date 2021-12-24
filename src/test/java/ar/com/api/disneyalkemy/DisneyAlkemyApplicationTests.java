package ar.com.api.disneyalkemy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.api.disneyalkemy.entities.Personaje;
import ar.com.api.disneyalkemy.services.PersonajeService;
import ar.com.api.disneyalkemy.services.PersonajeService.ValidacionPersonajeEnum;

@SpringBootTest
class DisneyAlkemyApplicationTests {

	@Autowired
	private PersonajeService service;

	@Test
	void contextLoads() {
	}

	@Test
	void personajeAtributoVacio(){
		Personaje p = new Personaje();
		p.setEdad(30);
		p.setImagen("abcd");
		assertTrue(service.validacionCreacion(p) == ValidacionPersonajeEnum.FALTA_ATRIBUTO);
	}

}
