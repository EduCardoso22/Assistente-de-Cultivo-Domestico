package unit;

import mocks.RepositorioDadosMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.CultivoService;

import java.time.LocalDate;

public class ValidacaoEntradaTest {
    private RepositorioDadosMock mockRepository;
    private CultivoService service;

    @Before
    public void setUp() {
        mockRepository = new RepositorioDadosMock();
        service = new CultivoService(mockRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testErroCadastrarPlantaComEspecieNaoExistente() {
        service.cadastrarPlanta("Minha Planta", "Especie Inexistente", LocalDate.now());
    }

    @Test
    public void testErroCadastrarPlantaComMesmoApelido() {
        mockRepository.salvarEspecie(new model.Especie("Samambaia", 2, 30, 90));
        service.cadastrarPlanta("Planta Repetida", "Samambaia", LocalDate.now());
        
        try {
            service.cadastrarPlanta("Planta Repetida", "Samambaia", LocalDate.now());
            Assert.fail("Deveria ter lançado IllegalArgumentException devido ao apelido repetido.");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Já existe uma planta cadastrada com o apelido: Planta Repetida", e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testErroRegistrarAtividadeEmPlantaInexistente() {
        service.registrarAtividade("Planta Fantasma", "REGA", LocalDate.now(), "Rega teste");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testErroRegistrarAtividadeTipoInvalido() {
        mockRepository.salvarEspecie(new model.Especie("Samambaia", 2, 30, 90));
        service.cadastrarPlanta("Planta Teste", "Samambaia", LocalDate.now());
        service.registrarAtividade("Planta Teste", "CONVERSAR", LocalDate.now(), "Conversar com a planta");
    }
}
