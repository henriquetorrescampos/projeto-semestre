package com.projeto_final.projeto_puc_go.Console;

import com.projeto_final.projeto_puc_go.Entity.*;
import com.projeto_final.projeto_puc_go.Service.*;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
import com.projeto_final.projeto_puc_go.Dto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final EvaluatedService evaluatedService;
    private final EvaluatorService evaluatorService;
    private final EvaluationService evaluationService;
    private final CharacteristicService characteristicService;
    private final RatingService ratingService;
    private final Scanner scanner;
    private final ConfigurableApplicationContext context;

    private Evaluator loggedInEvaluator;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";

    // Classe auxiliar interna para representar um indicador de habilidade pré-definido
    private static class SkillIndicator {
        String name;
        String description;
        String skillCategory;

        public SkillIndicator(String name, String description, String skillCategory) {
            this.name = name;
            this.description = description;
            this.skillCategory = skillCategory;
        }

        @Override
        public String toString() {
            return name + " (" + skillCategory + ") - " + description;
        }
    }

    // Lista de indicadores de habilidades baseada no PDF
    private static final List<SkillIndicator> predefinedSkillIndicators = new ArrayList<>();

    static {
        // Habilidades Administrativas
        predefinedSkillIndicators.add(new SkillIndicator(
                "Visão de Oportunidades/Inovações",
                "Elabora uma boa visão de oportunidades/inovações para o grupo e para a companhia",
                "ADMINISTRATIVE"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Facilitador de Mudanças",
                "Atua como facilitador para grandes mudanças que ocorrem no grupo e/ou na companhia",
                "ADMINISTRATIVE"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Comunicação de Prioridades Estratégicas",
                "Comunica as prioridades estratégicas de sua divisão/grupo/departamento",
                "ADMINISTRATIVE"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Consegue Recursos Necessários",
                "Consegue recursos necessários para a equipe",
                "ADMINISTRATIVE"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Cria Clima Produtivo",
                "Cria o clima produtivo necessário para a condução das tarefas",
                "ADMINISTRATIVE"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Conduz Planos e Cronogramas",
                "Conduz planos e cronogramas necessários",
                "ADMINISTRATIVE"
        ));

        // Habilidades Técnicas
        predefinedSkillIndicators.add(new SkillIndicator(
                "Boas Ideias para Tarefas Técnicas",
                "Apresenta boas idéias de como realizar tarefas na sua área de atuação específica",
                "TECHNICAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Conhecimento de Informações Relevantes",
                "Apresenta conhecimento sobre as informações relevantes ao trabalho de cada membro equipe",
                "TECHNICAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Entendimento de Técnicas e Métodos",
                "Tem um bom entendimento das técnicas e métodos aplicados em seu trabalho",
                "TECHNICAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Mentor para Equipe Técnica",
                "É um mentor para sua equipe técnica",
                "TECHNICAL"
        ));

        // Habilidades Pessoais
        predefinedSkillIndicators.add(new SkillIndicator(
                "Incentivo e Encorajamento",
                "Promove incentivo e encorajamento para as tarefas realizadas pela equipe",
                "PERSONAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Reconhecimento por Tarefa Realizada",
                "Promove reconhecimento por uma tarefa bem realizada",
                "PERSONAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Entusiasmo pelo Trabalho",
                "Promove o entusiasmo pelo trabalho de forma lúcida (com bom senso)",
                "PERSONAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Sensibilidade a Diferenças Individuais",
                "Possui sensibilidade e discernimento das diferenças individuais",
                "PERSONAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Conduz Pessoas a Trabalhar Juntas",
                "Conduz pessoas a trabalharem bem juntas",
                "PERSONAL"
        ));
        predefinedSkillIndicators.add(new SkillIndicator(
                "Promove Avaliações Críticas",
                "Promove avaliações críticas buscando o bom e o factível/realizável",
                "PERSONAL"
        ));
    }


    public ConsoleApplication(EvaluatedService evaluatedService, EvaluatorService evaluatorService,
                              EvaluationService evaluationService, CharacteristicService characteristicService,
                              RatingService ratingService, ConfigurableApplicationContext context) {
        this.evaluatedService = evaluatedService;
        this.evaluatorService = evaluatorService;
        this.evaluationService = evaluationService;
        this.characteristicService = characteristicService;
        this.ratingService = ratingService;
        this.scanner = new Scanner(System.in);
        this.context = context;
    }

    @Override
    public void run(String... args) {
        System.out.println(GREEN + "App Console Iniciado!" + RESET);
        exibirMenuPrincipal();
    }

    private void exibirMenuPrincipal() {
        int opt;
        do {
            System.out.println(CYAN + "\n--- MENU PRINCIPAL ---" + RESET);
            System.out.println("1. Entrar como Funcionário (Login)");
            System.out.println("2. Ver Cálculos Estatísticos");
            System.out.println("0. Sair");
            System.out.print(YELLOW + "Escolha: " + RESET);
            opt = lerInteiro();
            switch (opt) {
                case 1:
                    realizarLoginFuncionario();
                    break;
                case 2:
                    exibirMenuRelatorios();
                    break;
                case 0:
                    System.out.println(GREEN + "Saindo do aplicativo. Até mais!" + RESET);
                    scanner.close();
                    SpringApplication.exit(context, () -> 0);
                    System.exit(0);
                    break;
                default:
                    exibirErro("Opção inválida. Tente novamente.");
            }
        } while (opt != 0);
    }

    private void realizarLoginFuncionario() {
        System.out.println(CYAN + "\n--- LOGIN DO FUNCIONÁRIO ---" + RESET);
        listarAvaliadores();
        System.out.print("Digite seu ID de Avaliador para fazer login: ");
        Long evaluatorId = lerLongId();

        try {
            Optional<Evaluator> evaluatorOpt = evaluatorService.getEvaluatorById(evaluatorId);
            if (evaluatorOpt.isPresent()) {
                this.loggedInEvaluator = evaluatorOpt.get();
                System.out.println(GREEN + "Login realizado com sucesso! Bem-vindo(a), " + loggedInEvaluator.getName() + RESET);
                menuFuncionarioLogado();
            } else {
                exibirErro("ID de Avaliador não encontrado. Por favor, tente novamente.");
            }
        } catch (Exception e) {
            exibirErro("Erro ao tentar fazer login: " + e.getMessage());
        }
    }

    private void menuFuncionarioLogado() {
        int opt;
        do {
            System.out.println(CYAN + "\n--- MENU DO FUNCIONÁRIO LOGADO (" + loggedInEvaluator.getName() + ") ---" + RESET);
            System.out.println("1. Avaliar um Gerente");
            System.out.println("0. Fazer Logout");
            System.out.print(YELLOW + "Escolha: " + RESET);
            opt = lerInteiro();
            switch (opt) {
                case 1:
                    avaliarGerente();
                    break;
                case 0:
                    System.out.println(GREEN + "Logout realizado. Até mais!" + RESET);
                    this.loggedInEvaluator = null;
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (opt != 0 && this.loggedInEvaluator != null);
    }

    private void avaliarGerente() {
        if (loggedInEvaluator == null) {
            exibirErro("Você precisa estar logado para avaliar um gerente.");
            return;
        }

        System.out.println(CYAN + "\n--- AVALIAR GERENTE ---" + RESET);

        System.out.println("\n--- GERENTE A SER AVALIADO ---");
        listarAvaliados();
        System.out.print("ID do Gerente a ser avaliado: ");
        Long evaluatedId = lerLongId();

        ManagerType managerType = selectManagerType();

        try {
            Evaluated evaluated = evaluatedService.getEvaluatedById(evaluatedId)
                    .orElseThrow(() -> new ResourceNotFoundException("Gerente (Avaliado) não encontrado com ID: " + evaluatedId));

            // GERAR TÍTULO E DESCRIÇÃO PADRÃO
            String title = "Avaliação de Gerente por " + loggedInEvaluator.getName() + " em " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String description = "Avaliação de habilidades do gerente " + evaluated.getName() + " realizada por " + loggedInEvaluator.getName() + ".";
            LocalDateTime now = LocalDateTime.now();


            Evaluation newEvaluation = Evaluation.builder()
                    .title(title)
                    .description(description)
                    .startDate(now)
                    .endDate(now)
                    .evaluated(evaluated)
                    .evaluator(loggedInEvaluator)
                    .managerType(managerType)
                    .build();

            Set<Characteristic> characteristics = new HashSet<>();
            System.out.println(CYAN + "\n--- SELECIONAR CARACTERÍSTICAS PARA AVALIAR ---" + RESET);

            String continueSelectingChars = "s";
            do {
                System.out.println(PURPLE + "\nSelecione a Categoria de Habilidade:" + RESET);
                System.out.println("1. Administrativa");
                System.out.println("2. Técnica");
                System.out.println("3. Pessoal");
                System.out.println("0. Concluir seleção de categorias (se não quiser mais adicionar)");
                System.out.print(YELLOW + "Opção: " + RESET);
                int categoryChoice = lerInteiro();

                String selectedCategory = null;
                boolean skipToNextIteration = false;

                switch (categoryChoice) {
                    case 1: selectedCategory = "ADMINISTRATIVE"; break;
                    case 2: selectedCategory = "TECHNICAL"; break;
                    case 3: selectedCategory = "PERSONAL"; break;
                    case 0:
                        continueSelectingChars = "n";
                        skipToNextIteration = true;
                        break;
                    default:
                        exibirErro("Opção inválida. Tente novamente.");
                        skipToNextIteration = true;
                        break;
                }

                if (skipToNextIteration) {
                    continue;
                }

                if (selectedCategory != null) {
                    final String categoryToFilter = selectedCategory; // Cópia final efetiva

                    List<SkillIndicator> indicatorsForCategory = predefinedSkillIndicators.stream()
                            .filter(si -> si.skillCategory.equalsIgnoreCase(categoryToFilter))
                            .collect(Collectors.toList());

                    if (indicatorsForCategory.isEmpty()) {
                        System.out.println(YELLOW + "Nenhum indicador pré-definido para esta categoria." + RESET);
                    } else {
                        System.out.println(BLUE + "\nIndicadores Disponíveis para " + selectedCategory + ":" + RESET);
                        for (int i = 0; i < indicatorsForCategory.size(); i++) {
                            System.out.println((i + 1) + ". " + indicatorsForCategory.get(i).name);
                        }
                        System.out.print(YELLOW + "Digite o(s) número(s) do(s) indicador(es) para avaliar (ex: 1,3,5): " + RESET);
                        String input = scanner.nextLine();
                        Set<Integer> choices = new HashSet<>();
                        try {
                            choices = Arrays.stream(input.split(","))
                                    .map(String::trim)
                                    .filter(s -> !s.isEmpty())
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toSet());
                        } catch (NumberFormatException e) {
                            exibirErro("Entrada inválida. Digite apenas números separados por vírgula.");
                            continue;
                        }


                        for (Integer choice : choices) {
                            if (choice > 0 && choice <= indicatorsForCategory.size()) {
                                SkillIndicator chosenIndicator = indicatorsForCategory.get(choice - 1);

                                Characteristic characteristic = Characteristic.builder()
                                        .name(chosenIndicator.name)
                                        .description(chosenIndicator.description)
                                        .skillCategory(chosenIndicator.skillCategory)
                                        .evaluation(newEvaluation)
                                        .build();

                                System.out.println(PURPLE + "\n--- Avaliando: " + chosenIndicator.name + " (" + chosenIndicator.skillCategory + ") ---" + RESET);
                                Set<Rating> ratings = new HashSet<>();
                                System.out.print("Pontuação (1-5): ");
                                Integer score = lerInteiro();
                                if (score < 1 || score > 5) {
                                    exibirErro("A pontuação deve ser entre 1 e 5. Esta característica não será salva.");
                                    continue;
                                }
                                // Removida a solicitação de comentário

                                Rating rating = Rating.builder()
                                        .score(score)
                                        .comment(null) // Comentário agora é nulo ou string vazia
                                        .evaluator(loggedInEvaluator)
                                        .characteristic(characteristic)
                                        .evaluation(newEvaluation)
                                        .build();
                                ratings.add(rating);

                                characteristic.setRatings(ratings);
                                characteristics.add(characteristic);
                            } else {
                                exibirErro("Número de indicador inválido: " + choice);
                            }
                        }
                    }
                }
                if (continueSelectingChars.equalsIgnoreCase("s")) {
                    System.out.print("\nDeseja selecionar mais características de outras categorias? (s/n): ");
                    continueSelectingChars = scanner.nextLine();
                }
            } while (continueSelectingChars.equalsIgnoreCase("s"));

            if (characteristics.isEmpty()) {
                System.out.println(YELLOW + "Nenhuma característica foi avaliada nesta sessão. Avaliação não será criada." + RESET);
                return;
            }

            newEvaluation.setCharacteristics(characteristics);

            evaluationService.createEvaluation(newEvaluation);
            System.out.println(GREEN + "Avaliação do gerente criada com sucesso!" + RESET);

        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (IllegalArgumentException e) {
            exibirErro("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao criar avaliação do gerente: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --- Métodos de Relatórios (Mantidos) ---
    private void exibirMenuRelatorios() {
        int opt;
        do {
            System.out.println(CYAN + "\n--- RELATÓRIOS ---" + RESET);
            System.out.println("1. Dist. por Gestor\n2. Média por Gestor\n3. Resumos\n4. Média por Gestor e Habilidade\n0. Voltar");
            System.out.print(YELLOW + "Escolha: " + RESET);
            opt = lerInteiro();
            switch (opt) {
                case 1:
                    System.out.println(CYAN + "\n--- DISTRIBUIÇÃO DE AVALIAÇÕES POR TIPO DE GESTOR ---" + RESET);
                    evaluationService.getManagerTypeDistribution().forEach(dto ->
                            System.out.printf("Tipo Gestor: %s, Contagem: %d\n", dto.getManagerType().getDisplayValue(), dto.getCount()));
                    break;
                case 2:
                    System.out.println(CYAN + "\n--- MÉDIA DE PONTUAÇÃO POR TIPO DE GESTOR ---" + RESET);
                    evaluationService.getAverageScoreByManagerType().forEach(dto ->
                            System.out.printf("Tipo Gestor: %s, Média: %.2f\n", dto.getManagerType().getDisplayValue(), dto.getAverageScore()));
                    break;
                case 3:
                    System.out.println(CYAN + "\n--- RESUMOS DE AVALIAÇÕES ---" + RESET);
                    evaluationService.getEvaluationSummaries().forEach(summary ->
                            System.out.printf("ID: %d, Título: %s, Avaliado: %s, Avaliador: %s, Média: %.2f\n",
                                    summary.getId(), summary.getTitle(), summary.getEvaluatedName(),
                                    summary.getEvaluatorName(), summary.getAverageScore()));
                    break;
                case 4:
                    System.out.println(CYAN + "\n--- MÉDIA DE PONTUAÇÃO POR TIPO DE GESTOR E CATEGORIA DE HABILIDADE ---" + RESET);
                    evaluationService.getAverageScoreByManagerTypeAndSkillCategory().stream()
                            .collect(Collectors.groupingBy(ManagerTypeSkillAverageDto::getManagerType))
                            .forEach((mt, skills) -> {
                                System.out.println("Tipo Gestor: " + mt.getDisplayValue());
                                skills.forEach(s ->
                                        System.out.printf("  - Categoria: %s, Média: %.2f\n", s.getSkillCategory(), s.getAverageScore()));
                            });
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (opt != 0);
    }

    // --- Métodos de Listagem Auxiliares (Simplificados, usados apenas para seleção de IDs) ---
    private void listarAvaliados() {
        System.out.println(CYAN + "\n--- Gerentes/Avaliados Disponíveis ---" + RESET);
        List<Evaluated> evaluateds = evaluatedService.getAllEvaluated();
        if (evaluateds.isEmpty()) {
            System.out.println(YELLOW + "Nenhum gerente encontrado. Cadastre um gerente primeiro (via SQL ou ferramenta de BD)." + RESET);
        } else {
            evaluateds.forEach(e -> System.out.printf("ID: %d, Nome: %s\n", e.getId(), e.getName()));
        }
    }

    private void listarAvaliadores() {
        System.out.println(CYAN + "\n--- Avaliadores (Funcionários) Disponíveis ---" + RESET);
        List<Evaluator> evaluators = evaluatorService.getAllEvaluators();
        if (evaluators.isEmpty()) {
            System.out.println(YELLOW + "Nenhum avaliador encontrado. Cadastre um avaliador primeiro (via SQL ou ferramenta de BD)." + RESET);
        } else {
            evaluators.forEach(e -> System.out.printf("ID: %d, Nome: %s\n", e.getId(), e.getName()));
        }
    }


    // --- Métodos Auxiliares Comuns (Expandidos para melhor leitura) ---
    private Long lerLongId() {
        while (!scanner.hasNextLong()) {
            exibirErro("Inválido. Digite um número.");
            scanner.next();
        }
        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }

    private Integer lerInteiro() {
        while (!scanner.hasNextInt()) {
            exibirErro("Inválido. Digite um número.");
            scanner.next();
        }
        Integer val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private LocalDateTime readDateTime(String prompt) {
        LocalDateTime dt = null;
        while (dt == null) {
            System.out.print(prompt);
            dt = parseDateTime(scanner.nextLine());
        }
        return dt;
    }

    private LocalDateTime readDateTimeOptional(String prompt) {
        System.out.print(prompt + " (ENTER para manter): ");
        return parseDateTime(scanner.nextLine());
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            if (!dateTimeString.isEmpty()) {
                exibirErro("Formato inválido. Use AAAA-MM-DD HH:MM.");
            }
            return null;
        }
    }

    private <T> void exibirLista(List<T> lista) {
        System.out.println(CYAN + "\n--- LISTA ---" + RESET);
        if (lista.isEmpty()) {
            System.out.println(YELLOW + "Nenhum item." + RESET);
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void exibirErro(String msg) {
        System.err.println(RED + "ERRO: " + msg + RESET);
    }

    private boolean confirm(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().equalsIgnoreCase("s");
    }

    private ManagerType selectManagerType() {
        ManagerType mType = null;
        while (mType == null) {
            System.out.println("Tipo de Gestor:");
            for (ManagerType type : ManagerType.values()) {
                System.out.println(type.ordinal() + 1 + ". " + type.getDisplayValue());
            }
            System.out.print("Opção: ");
            int opt = lerInteiro();
            try {
                mType = ManagerType.values()[opt - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                exibirErro("Opção inválida.");
            }
        }
        return mType;
    }
}