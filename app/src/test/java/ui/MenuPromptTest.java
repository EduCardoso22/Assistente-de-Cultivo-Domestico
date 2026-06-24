package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CultivoService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuPromptTest {

    @Mock
    private CultivoService service;

    private final InputStream systemInBackup = System.in;
    private final PrintStream systemOutBackup = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(systemInBackup);
        System.setOut(systemOutBackup);
    }

    /**
     * Helper para simular a digitação sequencial do usuário no console.
     */
    private void simularEntradaUsuario(String... comandos) {
        String inputSimulado = String.join("\n", comandos) + "\n";
        System.setIn(new ByteArrayInputStream(inputSimulado.getBytes()));
    }

    @Test
    public void testMenuOpcaoSairImediatamente() {
        simularEntradaUsuario("6");

        MenuPrompt menu = new MenuPrompt(service);
        menu.iniciar();

        String resultadoConsole = outputStreamCaptor.toString();

        assertTrue(resultadoConsole.contains("Encerrando o Assistente de Cultivo. Cuide bem das suas plantas!"));
        verify(service, times(1)).obterAlertas(any(LocalDate.class)); // Executado ao iniciar
    }

    @Test
    public void testCadastrarEspecieComSucesso() {
        simularEntradaUsuario("1", "Jiboia", "4", "30", "60", "66", "6");

        MenuPrompt menu = new MenuPrompt(service);
        menu.iniciar();

        verify(service).cadastrarEspecie("Jiboia", 4, 30, 60);

        String resultadoConsole = outputStreamCaptor.toString();
        assertTrue(resultadoConsole.contains("[SUCESSO] Espécie 'Jiboia' cadastrada com sucesso!"));
    }

    @Test
    public void testCadastrarPlantaSemEspeciesDisponiveis() {
        when(service.obterEspecies()).thenReturn(Collections.emptyList());

        simularEntradaUsuario("2", "6");

        MenuPrompt menu = new MenuPrompt(service);
        menu.iniciar();

        String resultadoConsole = outputStreamCaptor.toString();
        assertTrue(resultadoConsole.contains("[ERRO] Não há espécies cadastradas. Cadastre uma espécie primeiro."));
    }

    @Test
    public void testTratamentoErroEntradaInvalidaDeNumero() {
        simularEntradaUsuario("texto_invalido", "6");

        MenuPrompt menu = new MenuPrompt(service);
        menu.iniciar();

        String resultadoConsole = outputStreamCaptor.toString();
        assertTrue(resultadoConsole.contains("[ERRO] Entrada inválida. Por favor, digite um número inteiro."));
    }
}