package br.com.project.prontpet.models;

import br.com.project.prontpet.enums.Sex;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

    @Test
    void deveCriarPetComBuilder() {
        Pet pet = Pet.builder()
                .id(1L)
                .name("Rex")
                .species("Cachorro")
                .breed("Golden Retriever")
                .birthDate(LocalDate.of(2022, 5, 10))
                .weight(25.5)
                .sex(Sex.Male)
                .build();

        assertNotNull(pet);
        assertEquals("Rex", pet.getName());
        assertEquals("Cachorro", pet.getSpecies());
        assertEquals("Golden Retriever", pet.getBreed());
        assertEquals(25.5, pet.getWeight());
        assertEquals(Sex.Male, pet.getSex());
    }

    @Test
    void deveAtualizarNomeEPesoDoPet() {
        Pet pet = new Pet();

        pet.setName("Bob");
        pet.setWeight(12.5);

        assertEquals("Bob", pet.getName());
        assertEquals(12.5, pet.getWeight());
    }
}