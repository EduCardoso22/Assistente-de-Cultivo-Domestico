package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class PlantaTest {
    Planta planta;
    Especie especie = new Especie("Samambaia", 2, 30, 90);
    LocalDate data = LocalDate.of(2026, 6, 24);

    @BeforeEach
    void setUp() {
        planta = new Planta("Samambaia Cozinha", especie, data);
    }

    @Test
    @DisplayName("Testando pegar os valores dos atributos de Planta")
    public void testGetAtributosPlanta() {
        assertEquals("Samambaia Cozinha", planta.getApelido());
        assertEquals(especie, planta.getEspecie());
        assertEquals(data, planta.getDataAquisicao());
    }

    @Test
    @DisplayName("Testando mudar o apelido da planta")
    public void testSetApelido() {
        planta.setApelido("Samambaia");
        assertEquals("Samambaia", planta.getApelido());
    }

    @Test
    @DisplayName("Testando mudar a Especia da Planta")
    public void testSetEspecie() {
        Especie especie = new Especie("Cacto", 2, 30, 90);
        planta.setEspecie(especie);
        assertEquals(especie, planta.getEspecie());
    }

    @Test
    @DisplayName("Testando mudar a DataAquisição da Planta")
    public void testSetDataAquisicao() {
        LocalDate dataAquisicao = LocalDate.now();
        planta.setDataAquisicao(dataAquisicao);
        assertEquals(dataAquisicao, planta.getDataAquisicao());
    }

    @Test
    @DisplayName("Testando toString")
    public void testToString() {
        String plantaString =  "Samambaia Cozinha [Samambaia] - Adquirida em: 2026-06-24";
        assertEquals(plantaString, planta.toString());
    }
}
