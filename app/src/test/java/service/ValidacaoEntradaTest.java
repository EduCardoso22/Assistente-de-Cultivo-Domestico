package service; // Nota: Se for seguir o espelhamento, o ideal é mover para 'service' depois

import mocks.RepositorioDadosMock;
import model.Especie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class ValidacaoEntradaTest {
    private RepositorioDadosMock mockRepository;
    private CultivoService service;

    @BeforeEach
    public void setUp() {
        mockRepository = new RepositorioDadosMock();
        service = new CultivoService(mockRepository);
    }

    @Test
    public void testErroCadastrarPlantaComEspecieNaoExistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarPlanta("Minha Planta", "Especie Inexistente", LocalDate.now());
        });
    }

    @Test
    public void testErroCadastrarPlantaComMesmoApelido() {
        mockRepository.salvarEspecie(new Especie("Samambaia", 2, 30, 90));
        service.cadastrarPlanta("Planta Repetida", "Samambaia", LocalDate.now());

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarPlanta("Planta Repetida", "Samambaia", LocalDate.now());
        });

        assertEquals("Já existe uma planta cadastrada com o apelido: Planta Repetida", excecao.getMessage());
    }

    @Test
    public void testErroRegistrarAtividadeEmPlantaInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarAtividade("Planta Fantasma", "REGA", LocalDate.now(), "Rega teste");
        });
    }

    @Test
    public void testErroRegistrarAtividadeTipoInvalido() {
        mockRepository.salvarEspecie(new Especie("Samambaia", 2, 30, 90));
        service.cadastrarPlanta("Planta Teste", "Samambaia", LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarAtividade("Planta Teste", "CONVERSAR", LocalDate.now(), "Conversar com a planta");
        });
    }
}