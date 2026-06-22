package ui;

import model.Especie;
import model.Planta;
import model.RegistroDiario;
import service.CultivoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuPrompt {
    private final CultivoService service;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MenuPrompt(CultivoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("==========================================================");
        System.out.println("          ASSISTENTE DE CULTIVO DOMÉSTICO (CLI)           ");
        System.out.println("==========================================================");

        exibirAlertas();

        boolean rodando = true;
        while (rodando) {
            exibirOpcoesMenu();
            int opcao = lerOpcaoInteira();
            switch (opcao) {
                case 1:
                    cadastrarEspecie();
                    break;
                case 2:
                    cadastrarPlanta();
                    break;
                case 3:
                    registrarAtividade();
                    break;
                case 4:
                    exibirPainelGeral();
                    break;
                case 5:
                    exibirAlertas();
                    break;
                case 6:
                    System.out.println("\nEncerrando o Assistente de Cultivo. Cuide bem das suas plantas! 🌱");
                    rodando = false;
                    break;
                default:
                    System.out.println("\n[ERRO] Opção inválida. Digite um número de 1 a 6.");
            }
        }
    }

    private void exibirOpcoesMenu() {
        System.out.println("\n---------------- MENU PRINCIPAL ----------------");
        System.out.println("1. Cadastrar Nova Espécie");
        System.out.println("2. Cadastrar Nova Planta");
        System.out.println("3. Registrar Atividade de Cuidado (Rega, Poda, Adubação)");
        System.out.println("4. Exibir Painel Geral de Plantas");
        System.out.println("5. Exibir Alertas Pendentes");
        System.out.println("6. Sair do Sistema");
        System.out.println("------------------------------------------------");
    }

    private int lerOpcaoInteira() {
        while (true) {
            try {
                System.out.print("Escolha uma opção: ");
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    private void cadastrarEspecie() {
        System.out.println("\n--- CADASTRAR NOVA ESPÉCIE ---");
        System.out.print("Nome da espécie (ex: Samambaia): ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("[ERRO] O nome da espécie não pode ser vazio.");
            return;
        }

        System.out.print("Intervalo de Rega ideal (em dias): ");
        int rega = lerInteiroPositivo();

        System.out.print("Intervalo de Adubação ideal (em dias): ");
        int adubacao = lerInteiroPositivo();

        System.out.print("Intervalo de Poda ideal (em dias): ");
        int poda = lerInteiroPositivo();

        service.cadastrarEspecie(nome, rega, adubacao, poda);
        System.out.println("[SUCESSO] Espécie '" + nome + "' cadastrada com sucesso!");
    }

    private void cadastrarPlanta() {
        System.out.println("\n--- CADASTRAR NOVA PLANTA ---");
        
        List<Especie> especies = service.obterEspecies();
        if (especies.isEmpty()) {
            System.out.println("[ERRO] Não há espécies cadastradas. Cadastre uma espécie primeiro.");
            return;
        }

        System.out.println("Espécies disponíveis:");
        for (int i = 0; i < especies.size(); i++) {
            System.out.println((i + 1) + ". " + especies.get(i).getNome());
        }

        System.out.print("Selecione o número da espécie correspondente: ");
        int index = lerOpcaoInteira() - 1;
        if (index < 0 || index >= especies.size()) {
            System.out.println("[ERRO] Seleção de espécie inválida.");
            return;
        }
        String nomeEspecie = especies.get(index).getNome();

        System.out.print("Digite um apelido único para a sua planta (ex: Samambaia da Sala): ");
        String apelido = scanner.nextLine().trim();
        if (apelido.isEmpty()) {
            System.out.println("[ERRO] O apelido não pode ser vazio.");
            return;
        }

        System.out.print("Digite a data de aquisição (Formato AAAA-MM-DD ou pressione Enter para Hoje): ");
        String dataInput = scanner.nextLine().trim();
        LocalDate dataAquisicao;
        if (dataInput.isEmpty()) {
            dataAquisicao = LocalDate.now();
        } else {
            try {
                dataAquisicao = LocalDate.parse(dataInput, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("[ERRO] Formato de data inválido. Cadastro cancelado.");
                return;
            }
        }

        try {
            service.cadastrarPlanta(apelido, nomeEspecie, dataAquisicao);
            System.out.println("[SUCESSO] Planta '" + apelido + "' cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private void registrarAtividade() {
        System.out.println("\n--- REGISTRAR ATIVIDADE DE CUIDADO ---");
        List<Planta> plantas = service.obterPlantas();
        if (plantas.isEmpty()) {
            System.out.println("[ERRO] Nenhuma planta cadastrada.");
            return;
        }

        System.out.println("Suas plantas:");
        for (int i = 0; i < plantas.size(); i++) {
            System.out.println((i + 1) + ". " + plantas.get(i).getApelido() + " (" + plantas.get(i).getEspecie().getNome() + ")");
        }

        System.out.print("Selecione a planta pelo número: ");
        int index = lerOpcaoInteira() - 1;
        if (index < 0 || index >= plantas.size()) {
            System.out.println("[ERRO] Planta inválida.");
            return;
        }
        String apelidoPlanta = plantas.get(index).getApelido();

        System.out.println("Selecione o tipo de atividade:");
        System.out.println("1. Regar");
        System.out.println("2. Adubar");
        System.out.println("3. Podar");
        int tipoOp = lerOpcaoInteira();
        String tipo;
        if (tipoOp == 1) tipo = "REGA";
        else if (tipoOp == 2) tipo = "ADUBACAO";
        else if (tipoOp == 3) tipo = "PODA";
        else {
            System.out.println("[ERRO] Opção de atividade inválida.");
            return;
        }

        System.out.print("Digite a data da atividade (Formato AAAA-MM-DD ou pressione Enter para Hoje): ");
        String dataInput = scanner.nextLine().trim();
        LocalDate dataAtividade;
        if (dataInput.isEmpty()) {
            dataAtividade = LocalDate.now();
        } else {
            try {
                dataAtividade = LocalDate.parse(dataInput, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("[ERRO] Formato de data inválido. Registro cancelado.");
                return;
            }
        }

        System.out.print("Digite alguma observação (opcional): ");
        String obs = scanner.nextLine().trim();

        try {
            service.registrarAtividade(apelidoPlanta, tipo, dataAtividade, obs);
            System.out.println("[SUCESSO] Atividade de " + tipo + " registrada para a planta '" + apelidoPlanta + "'!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private void exibirPainelGeral() {
        System.out.println("\n======================= PAINEL GERAL DE CULTIVO =======================");
        List<Planta> plantas = service.obterPlantas();
        if (plantas.isEmpty()) {
            System.out.println("Nenhuma planta cadastrada no momento. Adicione plantas para monitorar.");
            System.out.println("=======================================================================");
            return;
        }

        System.out.printf("%-15s | %-12s | %-12s | %-12s | %-12s | %-12s%n",
                "Apelido", "Espécie", "Aquisição", "Últ. Rega", "Últ. Adub.", "Últ. Poda");
        System.out.println("-----------------------------------------------------------------------");
        for (Planta p : plantas) {
            LocalDate ultimaRega = p.getUltimaDataAtividade("REGA");
            LocalDate ultimaAdubacao = p.getUltimaDataAtividade("ADUBACAO");
            LocalDate ultimaPoda = p.getUltimaDataAtividade("PODA");

            String urStr = (p.getHistoricoAtividades().stream().anyMatch(r -> r.getTipoAtividade().equals("REGA"))) ? ultimaRega.toString() : "Nunca";
            String uaStr = (p.getHistoricoAtividades().stream().anyMatch(r -> r.getTipoAtividade().equals("ADUBACAO"))) ? ultimaAdubacao.toString() : "Nunca";
            String upStr = (p.getHistoricoAtividades().stream().anyMatch(r -> r.getTipoAtividade().equals("PODA"))) ? ultimaPoda.toString() : "Nunca";

            System.out.printf("%-15s | %-12s | %-12s | %-12s | %-12s | %-12s%n",
                    p.getApelido(),
                    p.getEspecie().getNome(),
                    p.getDataAquisicao().toString(),
                    urStr,
                    uaStr,
                    upStr);
        }
        System.out.println("=======================================================================");
        
        System.out.println("\n--- Histórico de Atividades Detalhado ---");
        for (Planta p : plantas) {
            System.out.println("• Planta: " + p.getApelido());
            if (p.getHistoricoAtividades().isEmpty()) {
                System.out.println("  Sem atividades registradas.");
            } else {
                for (RegistroDiario r : p.getHistoricoAtividades()) {
                    System.out.println("  - " + r.toString());
                }
            }
        }
    }

    private void exibirAlertas() {
        System.out.println("\n>>> ALERTAS DE CUIDADO Pendentes (Hoje: " + LocalDate.now() + ") <<<");
        List<String> alertas = service.obterAlertas(LocalDate.now());
        if (alertas.isEmpty()) {
            System.out.println("🌱 Tudo sob controle! Suas plantas estão com os cuidados em dia.");
        } else {
            for (String alerta : alertas) {
                System.out.println("⚠️ " + alerta);
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private int lerInteiroPositivo() {
        while (true) {
            int valor = lerOpcaoInteira();
            if (valor > 0) {
                return valor;
            }
            System.out.println("[ERRO] O valor deve ser um número inteiro maior que 0.");
        }
    }
}
