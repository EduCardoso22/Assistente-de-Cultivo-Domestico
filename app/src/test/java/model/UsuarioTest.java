package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("Maria");
    }

    @Test
    @DisplayName("Testando pegar o nome")
    public void testGetNome() {
        assertEquals("Maria", usuario.getNome());
    }

    @Test
    @DisplayName("Testando Mudar o nome")
    public void testSetNome() {
        usuario.setNome("Ana");
        assertEquals("Ana", usuario.getNome());
    }
}
