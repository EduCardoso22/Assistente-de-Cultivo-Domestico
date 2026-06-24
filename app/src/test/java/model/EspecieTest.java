package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EspecieTest {
    private Especie especie;

    @BeforeEach
    public void setUp() {
        especie = new Especie("Samambaia",2, 30, 90);
    }

    @Test
    @DisplayName("Testando pegar os valores dos atributos da Especie")
    public void testGetAtributos() {
        assertEquals("Samambaia", especie.getNome());
        assertEquals(2, especie.getIntervaloRegaDias());
        assertEquals(30, especie.getIntervaloAdubacaoDias());
        assertEquals(90, especie.getIntervaloPodaDias());
    }

    @Test
    @DisplayName("Testando mudar o nome da Especie")
    public void testSetNome() {
        especie.setNome("Cacto");
        assertEquals("Cacto", especie.getNome());
    }

    @Test
    @DisplayName("Testando mudar o intervalo de dias de rega")
    public void testSetIntervaloRegaDias() {
        especie.setIntervaloRegaDias(10);
        assertEquals(10, especie.getIntervaloRegaDias());
    }

    @Test
    @DisplayName("Testando mudar o intervalo de dias de adubacao")
    public void testSetIntervaloAdubacaoDias() {
        especie.setIntervaloAdubacaoDias(60);
        assertEquals(60, especie.getIntervaloAdubacaoDias());
    }

    @Test
    @DisplayName("Testando mudar o intervalo de dias de poda")
    public void testSetIntervaloPodaDias() {
        especie.setIntervaloPodaDias(120);
        assertEquals(120, especie.getIntervaloPodaDias());
    }

    @Test
    @DisplayName("Testando o toString")
    public void testToString() {
        String expected = "Samambaia (Rega: 2d, Adubação: 30d, Poda: 90d)";
        assertEquals(expected, especie.toString());
    }

}
