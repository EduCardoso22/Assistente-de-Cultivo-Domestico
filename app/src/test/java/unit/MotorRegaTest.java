package unit;

import mocks.RepositorioDadosMock;
import model.Especie;
import model.Planta;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.CultivoService;

import java.time.LocalDate;
import java.util.List;

public class MotorRegaTest {
    private RepositorioDadosMock mockRepository;
    private CultivoService service;

    @Before
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
        Assert.assertFalse("Não deveria ter alerta de rega no dia da aquisição", temRegaPendente);
    }

    @Test
    public void testAlertaRegaPendenteNoDiaExato() {
        LocalDate hoje = LocalDate.now();
        LocalDate doisDiasAtras = hoje.minusDays(2);
        service.cadastrarPlanta("Planta Teste", "Samambaia", doisDiasAtras);

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaPendente = alertas.stream().anyMatch(a -> a.contains("Planta Teste") && a.contains("Rega pendente"));
        Assert.assertTrue("Deveria ter alerta de rega pendente hoje", temRegaPendente);
    }

    @Test
    public void testAlertaRegaAtrasada() {
        LocalDate hoje = LocalDate.now();
        LocalDate cincoDiasAtras = hoje.minusDays(5);
        service.cadastrarPlanta("Planta Teste", "Samambaia", cincoDiasAtras);

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaAtrasada = alertas.stream().anyMatch(a -> a.contains("Planta Teste") && a.contains("Rega atrasada há 3 dia(s)"));
        Assert.assertTrue("Deveria indicar que a rega está atrasada há 3 dias", temRegaAtrasada);
    }

    @Test
    public void testRegaRemoveAlerta() {
        LocalDate hoje = LocalDate.now();
        LocalDate cincoDiasAtras = hoje.minusDays(5);
        service.cadastrarPlanta("Planta Teste", "Samambaia", cincoDiasAtras);

        service.registrarAtividade("Planta Teste", "REGA", hoje.minusDays(1), "Rega efetuada");

        List<String> alertas = service.obterAlertas(hoje);
        boolean temRegaPendente = alertas.stream().anyMatch(a -> a.contains("Planta Teste") && a.contains("Rega"));
        Assert.assertFalse("Alerta de rega deveria ter sido removido após a rega de ontem", temRegaPendente);
    }
}
