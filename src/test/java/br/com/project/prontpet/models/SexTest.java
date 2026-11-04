package br.com.project.prontpet.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SexTest {

    @Test
    void deveConterTodosOsValoresDeSexo() {
        Sex[] valores = Sex.values();

        assertEquals(3, valores.length);
        assertArrayEquals(
                new Sex[]{Sex.Female, Sex.Male, Sex.Other},
                valores
        );
    }
}