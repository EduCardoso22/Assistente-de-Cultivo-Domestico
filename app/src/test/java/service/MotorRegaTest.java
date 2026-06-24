package service; // Nota: Lembre-se de mover para o pacote espelhado 'service' depois se for seguir a organização anterior!

import mocks.RepositorioDadosMock;
import model.Especie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

public class MotorRegaTest {
    private RepositorioDadosMock mockRepository;
    private CultivoService service;

    @BeforeEach
    public void setUp() {
        mockRepository = new RepositorioDadosMock();
        service = new CultivoService(mockRepository);
        mockRepository.salvarEspecie(new Especie("Samambaia", 2, 30, 90));
    }

    @Test
    public void testSemAlertasQuandoCuidadoEDia() {
        LocalDate hoje = LocalDate.now();
        service.cadastrarPlanta("Planta Teste", "Samambaia", hoje);

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaPendente = alertas.stream().anyMatch(a -> a.contains("Rega"));

        assertFalse(temRegaPendente, "Não deveria ter alerta de rega no dia da aquisição");
    }

    @Test
    public void testAlertaRegaPendenteNoDiaExato() {
        LocalDate hoje = LocalDate.now();
        LocalDate doisDiasAtras = hoje.minusDays(2);
        service.cadastrarPlanta("Planta Teste", "Samambaia", doisDiasAtras);

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaPendente = alertas.stream().anyMatch(a -> a.contains("Planta Teste") && a.contains("Rega pendente"));

        assertTrue(temRegaPendente, "Deveria ter alerta de rega pendente hoje");
    }

    @Test
    public void testAlertaRegaAtrasada() {
        LocalDate hoje = LocalDate.now();
        LocalDate cincoDiasAtras = hoje.minusDays(5);
        service.cadastrarPlanta("Planta Teste", "Samambaia", cincoDiasAtras);

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaAtrasada = alertas.stream().anyMatch(a -> a.contains("Planta Teste") && a.contains("Rega atrasada há 3 dia(s)"));

        assertTrue(temRegaAtrasada, "Deveria indicar que a rega está atrasada há 3 dias");
    }

    @Test
    public void testRegaRemoveAlerta() {
        LocalDate hoje = LocalDate.now();
        LocalDate cincoDiasAtras = hoje.minusDays(5);
        service.cadastrarPlanta("Planta Teste", "Samambaia", cincoDiasAtras);

        service.registrarAtividade("Planta Teste", "REGA", hoje.minusDays(1), "Rega efetuada");

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaPendente = alertas.stream().anyMatch(a -> a.contains("Planta Teste") && a.contains("Rega"));

        assertFalse(temRegaPendente, "Alerta de rega deveria ter sido removido após a rega de ontem");
    }
}