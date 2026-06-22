package repository;

import model.Especie;
import model.Planta;
import model.RegistroDiario;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArquivoDadosRepository implements IDadosRepository {
    private final String nomeArquivo;
    private final List<Planta> plantas;
    private final List<Especie> especies;

    public ArquivoDadosRepository(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.plantas = new ArrayList<>();
        this.especies = new ArrayList<>();
    }

    @Override
    public void salvarPlanta(Planta planta) {
        plantas.removeIf(p -> p.getApelido().equalsIgnoreCase(planta.getApelido()));
        plantas.add(planta);
    }

    @Override
    public List<Planta> obterTodasPlantas() {
        return plantas;
    }

    @Override
    public void salvarEspecie(Especie especie) {
        especies.removeIf(e -> e.getNome().equalsIgnoreCase(especie.getNome()));
        especies.add(especie);
    }

    @Override
    public List<Especie> obterTodasEspecies() {
        return especies;
    }

    @Override
    public void salvarDados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Especie esp : especies) {
                writer.printf("ESPECIE;%s;%d;%d;%d%n",
                        esp.getNome(),
                        esp.getIntervaloRegaDias(),
                        esp.getIntervaloAdubacaoDias(),
                        esp.getIntervaloPodaDias());
            }

            for (Planta p : plantas) {
                writer.printf("PLANTA;%s;%s;%s%n",
                        p.getApelido(),
                        p.getEspecie().getNome(),
                        p.getDataAquisicao().toString());
                
                for (RegistroDiario reg : p.getHistoricoAtividades()) {
                    writer.printf("DIARIO;%s;%s;%s;%s%n",
                            p.getApelido(),
                            reg.getData().toString(),
                            reg.getTipoAtividade(),
                            reg.getObservacoes());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }

    @Override
    public void carregarDados() {
        File file = new File(nomeArquivo);
        if (!file.exists()) {
            inicializarEspeciesPadrao();
            salvarDados();
            return;
        }

        plantas.clear();
        especies.clear();

        List<String[]> plantaLines = new ArrayList<>();
        List<String[]> diarioLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split(";", -1);
                if (partes.length < 2) continue;

                switch (partes[0]) {
                    case "ESPECIE":
                        if (partes.length >= 5) {
                            String nome = partes[1];
                            int rega = Integer.parseInt(partes[2]);
                            int adubacao = Integer.parseInt(partes[3]);
                            int poda = Integer.parseInt(partes[4]);
                            especies.add(new Especie(nome, rega, adubacao, poda));
                        }
                        break;
                    case "PLANTA":
                        if (partes.length >= 4) {
                            plantaLines.add(partes);
                        }
                        break;
                    case "DIARIO":
                        if (partes.length >= 5) {
                            diarioLines.add(partes);
                        }
                        break;
                }
            }

            if (especies.isEmpty()) {
                inicializarEspeciesPadrao();
            }

            for (String[] partes : plantaLines) {
                String apelido = partes[1];
                String nomeEspecie = partes[2];
                LocalDate dataAquisicao = LocalDate.parse(partes[3]);

                Especie esp = especies.stream()
                        .filter(e -> e.getNome().equalsIgnoreCase(nomeEspecie))
                        .findFirst()
                        .orElseGet(() -> {
                            Especie nova = new Especie(nomeEspecie, 3, 30, 90);
                            especies.add(nova);
                            return nova;
                        });

                plantas.add(new Planta(apelido, esp, dataAquisicao));
            }

            for (String[] partes : diarioLines) {
                String apelidoPlanta = partes[1];
                LocalDate data = LocalDate.parse(partes[2]);
                String tipo = partes[3];
                String obs = partes[4];

                Planta p = plantas.stream()
                        .filter(pl -> pl.getApelido().equalsIgnoreCase(apelidoPlanta))
                        .findFirst()
                        .orElse(null);

                if (p != null) {
                    p.adicionarAtividade(new RegistroDiario(data, tipo, obs));
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar os dados do arquivo: " + e.getMessage());
            if (especies.isEmpty()) {
                inicializarEspeciesPadrao();
            }
        }
    }

    private void inicializarEspeciesPadrao() {
        especies.add(new Especie("Samambaia", 2, 30, 90));
        especies.add(new Especie("Cacto", 15, 60, 365));
        especies.add(new Especie("Suculenta", 7, 45, 180));
        especies.add(new Especie("Hortela", 1, 15, 30));
    }
}
