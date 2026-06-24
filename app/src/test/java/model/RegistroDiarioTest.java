package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class RegistroDiarioTest {
    LocalDate data = LocalDate.of(2026, 6, 24);
    RegistroDiario registroDiario;

    @BeforeEach
    public void setUp() {
        registroDiario = new RegistroDiario(data, "REGA", "Rega feitas ás 17h");
    }

    @Test
    @DisplayName("Testando pegar os atributos de RegitroDiario")
    public void pegarOsatributosDeRegitroDiario() {
        LocalDate localDate = LocalDate.of(2026, 6, 24);
        assertEquals(localDate, registroDiario.getData());
        assertEquals("REGA", registroDiario.getTipoAtividade());
        assertEquals("Rega feitas ás 17h", registroDiario.getObservacoes());
    }

    @Test
    @DisplayName("Testando mudar o atributo Data")
    public void mudarSetData() {
        LocalDate localDate = LocalDate.of(2026, 6, 20);
        registroDiario.setData(localDate);
        assertEquals(localDate, registroDiario.getData());
    }

    @Test
    @DisplayName("Testando mudar o atributo Atividade")
    public void mudarAtividade() {
        registroDiario.setTipoAtividade("PODA");
        assertEquals("PODA", registroDiario.getTipoAtividade());
    }

    @Test
    @DisplayName("Testando mudar o atributo Observações")
    public void mudarObservacoes() {
        registroDiario.setObservacoes("Rega feitas ás 19h");
        assertEquals("Rega feitas ás 19h", registroDiario.getObservacoes());
    }

    @Test
    @DisplayName("Testando o toString")
    public void toStringTest() {
        String string =  "[2026-06-24] REGA - Rega feitas ás 17h";
        assertEquals(string, registroDiario.toString());
    }
}
