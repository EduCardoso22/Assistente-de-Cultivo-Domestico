package service;

import model.Especie;
import model.Planta;
import model.RegistroDiario;
import repository.IDadosRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CultivoService {
    private final IDadosRepository repository;

    public CultivoService(IDadosRepository repository) {
        this.repository = repository;
    }

    public void cadastrarEspecie(String nome, int rega, int adubacao, int poda) {
        Especie esp = new Especie(nome, rega, adubacao, poda);
        repository.salvarEspecie(esp);
        repository.salvarDados();
    }

    public List<Especie> obterEspecies() {
        return repository.obterTodasEspecies();
    }

    public void cadastrarPlanta(String apelido, String nomeEspecie, LocalDate dataAquisicao) {
        Especie esp = repository.obterTodasEspecies().stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeEspecie))
                .findFirst()
                .orElse(null);

        if (esp == null) {
            throw new IllegalArgumentException("Espécie não cadastrada: " + nomeEspecie);
        }

        boolean jaExiste = repository.obterTodasPlantas().stream()
                .anyMatch(p -> p.getApelido().equalsIgnoreCase(apelido));
        if (jaExiste) {
            throw new IllegalArgumentException("Já existe uma planta cadastrada com o apelido: " + apelido);
        }

        Planta p = new Planta(apelido, esp, dataAquisicao);
        repository.salvarPlanta(p);
        repository.salvarDados();
    }

    public List<Planta> obterPlantas() {
        return repository.obterTodasPlantas();
    }

    public void registrarAtividade(String apelidoPlanta, String tipo, LocalDate data, String obs) {
        Planta planta = repository.obterTodasPlantas().stream()
                .filter(p -> p.getApelido().equalsIgnoreCase(apelidoPlanta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Planta não encontrada: " + apelidoPlanta));

        if (!tipo.equalsIgnoreCase("REGA") && !tipo.equalsIgnoreCase("ADUBACAO") && !tipo.equalsIgnoreCase("PODA")) {
            throw new IllegalArgumentException("Tipo de atividade inválido. Use REGA, ADUBACAO ou PODA.");
        }

        RegistroDiario registro = new RegistroDiario(data, tipo.toUpperCase(), obs);
        planta.adicionarAtividade(registro);
        repository.salvarDados();
    }

    public List<String> obterAlertas(LocalDate dataReferencia) {
        List<String> alertas = new ArrayList<>();
        for (Planta p : repository.obterTodasPlantas()) {
            Especie esp = p.getEspecie();

            // Rega
            LocalDate ultimaRega = p.getUltimaDataAtividade("REGA");
            long diasSemRega = ChronoUnit.DAYS.between(ultimaRega, dataReferencia);
            if (diasSemRega >= esp.getIntervaloRegaDias()) {
                long atraso = diasSemRega - esp.getIntervaloRegaDias();
                if (atraso == 0) {
                    alertas.add(String.format("[%s] Rega pendente hoje!", p.getApelido()));
                } else {
                    alertas.add(String.format("[%s] ATENÇÃO: Rega atrasada há %d dia(s)!", p.getApelido(), atraso));
                }
            }

            // Adubação
            LocalDate ultimaAdubacao = p.getUltimaDataAtividade("ADUBACAO");
            long diasSemAdubacao = ChronoUnit.DAYS.between(ultimaAdubacao, dataReferencia);
            if (diasSemAdubacao >= esp.getIntervaloAdubacaoDias()) {
                long atraso = diasSemAdubacao - esp.getIntervaloAdubacaoDias();
                if (atraso == 0) {
                    alertas.add(String.format("[%s] Adubação pendente hoje!", p.getApelido()));
                } else {
                    alertas.add(String.format("[%s] ATENÇÃO: Adubação atrasada há %d dia(s)!", p.getApelido(), atraso));
                }
            }

            // Poda
            LocalDate ultimaPoda = p.getUltimaDataAtividade("PODA");
            long diasSemPoda = ChronoUnit.DAYS.between(ultimaPoda, dataReferencia);
            if (diasSemPoda >= esp.getIntervaloPodaDias()) {
                long atraso = diasSemPoda - esp.getIntervaloPodaDias();
                if (atraso == 0) {
                    alertas.add(String.format("[%s] Poda pendente hoje!", p.getApelido()));
                } else {
                    alertas.add(String.format("[%s] ATENÇÃO: Poda atrasada há %d dia(s)!", p.getApelido(), atraso));
                }
            }
        }
        return alertas;
    }

    public void inicializarDados() {
        repository.carregarDados();
    }
}
