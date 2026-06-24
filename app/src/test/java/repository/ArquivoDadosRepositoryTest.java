package repository;

import model.Especie;
import model.Planta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArquivoDadosRepositoryTest {

    @TempDir
    Path pastaTemporaria;

    private String caminhoArquivo;
    private ArquivoDadosRepository repository;

    @BeforeEach
    public void setUp() {
        caminhoArquivo = pastaTemporaria.resolve("dados_teste.txt").toString();
        repository = new ArquivoDadosRepository(caminhoArquivo);
    }

    @Test
    public void testCarregarDadosEmArquivoInexistenteDeveInicializarPadroes() {
        File arquivo = new File(caminhoArquivo);
        assertFalse(arquivo.exists(), "O arquivo não deveria existir ainda.");

        repository.carregarDados();

        List<Especie> especies = repository.obterTodasEspecies();
        assertFalse(especies.isEmpty(), "Deveria ter carregado as espécies padrão.");
        assertEquals(4, especies.size(), "Deveria conter as 4 espécies padrão.");

        assertTrue(arquivo.exists(), "O repositório deveria ter criado o arquivo físico.");
    }

    @Test
    public void testSalvarECarregarDadosCicloCompleto() {
        Especie jiboia = new Especie("Jiboia", 4, 20, 60);
        Planta minhaPlanta = new Planta("Verdinha", jiboia, LocalDate.now());

        repository.salvarEspecie(jiboia);
        repository.salvarPlanta(minhaPlanta);

        repository.salvarDados();

        ArquivoDadosRepository novoRepository = new ArquivoDadosRepository(caminhoArquivo);
        novoRepository.carregarDados();

        List<Planta> plantasCarregadas = novoRepository.obterTodasPlantas();
        assertEquals(1, plantasCarregadas.size(), "Deveria ter carregado exatamente 1 planta.");
        assertEquals("Verdinha", plantasCarregadas.get(0).getApelido());
        assertEquals("Jiboia", plantasCarregadas.get(0).getEspecie().getNome());
    }

    @Test
    public void testSalvarEspecieDuplicadaDeveSobrescrever() {
        Especie cactoOriginal = new Especie("Cacto", 15, 60, 365);
        Especie cactoAtualizado = new Especie("Cacto", 10, 60, 365); // mudou intervalo de rega

        repository.salvarEspecie(cactoOriginal);
        repository.salvarEspecie(cactoAtualizado); // Deve remover a anterior com o mesmo nome

        List<Especie> especies = repository.obterTodasEspecies();
        assertEquals(1, especies.size(), "Não deveria duplicar a espécie na lista.");
        assertEquals(10, especies.get(0).getIntervaloRegaDias(), "Deveria ter atualizado os dados da espécie.");
    }
}