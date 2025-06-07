package com.projeto_final.projeto_puc_go.Console;

import com.projeto_final.projeto_puc_go.Entity.*;
import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
import com.projeto_final.projeto_puc_go.Service.EvaluatorService;
import com.projeto_final.projeto_puc_go.Service.EvaluationService;
import com.projeto_final.projeto_puc_go.Service.CharacteristicService;
import com.projeto_final.projeto_puc_go.Service.RatingService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
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

    private static final List<SkillIndicator> predefinedSkillIndicators = new ArrayList<>();

    static {
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
            System.out.println("1. Entrar como Funcionário");
            System.out.println("2. Ver Cálculos Estatísticos");
            System.out.println("0. Sair");
            System.out.print(YELLOW + "Escolha: " + RESET);
            opt = lerInteiro();
            switch (opt) {
                case 1:
                    realizarRegistroFuncionario();
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

    private void realizarRegistroFuncionario() {
        System.out.println(CYAN + "\n--- IDENTIFICAÇÃO DO FUNCIONÁRIO ---" + RESET);
        System.out.print("Digite seu NOME: ");
        String evaluatorName = scanner.nextLine();

        List<Evaluator> existingEvaluators = evaluatorService.getAllEvaluators().stream()
                .filter(e -> e.getName().equalsIgnoreCase(evaluatorName))
                .collect(Collectors.toList());

        if (!existingEvaluators.isEmpty()) {
            this.loggedInEvaluator = existingEvaluators.get(0);
            System.out.println(GREEN + "Identificado como avaliador existente: " + loggedInEvaluator.getName() + " (ID: " + loggedInEvaluator.getId() + ")" + RESET);
        } else {
            String newEmail = evaluatorName.toLowerCase().replace(" ", ".") + "@empresa.com";
            Evaluator newEvaluator = Evaluator.builder()
                    .name(evaluatorName)
                    .email(newEmail)
                    .build();
            this.loggedInEvaluator = evaluatorService.createEvaluator(newEvaluator);
            System.out.println(GREEN + "Novo avaliador registrado: " + loggedInEvaluator.getName() + " (ID: " + loggedInEvaluator.getId() + ")" + RESET);
        }
        menuFuncionarioLogado();
    }

    private void menuFuncionarioLogado() {
        int opt;
        do {
            System.out.println(CYAN + "\n--- MENU DO FUNCIONÁRIO LOGADO (" + loggedInEvaluator.getName() + ") ---" + RESET);
            System.out.println("1. Avaliar um Gerente");
            System.out.println("2. Ver Detalhes de Avaliação");
            System.out.println("0. Fazer Logout");
            System.out.print(YELLOW + "Escolha: " + RESET);
            opt = lerInteiro();
            switch (opt) {
                case 1:
                    avaliarGerente();
                    break;
                case 2:
                    verDetalhesAvaliacao();
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
            exibirErro("Você precisa estar identificado para avaliar um gerente.");
            return;
        }

        System.out.println(CYAN + "\n--- AVALIAR GERENTE ---" + RESET);

        System.out.println("\n--- GERENTE A SER AVALIADO ---");
        listarAvaliados();
        System.out.print("ID do Gerente a ser avaliado: ");
        Long evaluatedId = lerLongId();

        try {
            Evaluated evaluated = evaluatedService.getEvaluatedById(evaluatedId)
                    .orElseThrow(() -> new ResourceNotFoundException("Gerente (Avaliado) não encontrado com ID: " + evaluatedId));

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
                    .build();

            Set<Characteristic> characteristics = new HashSet<>();
            Map<String, Double> categoryScores = new HashMap<>();
            Map<String, Integer> categoryCounts = new HashMap<>();

            System.out.println(CYAN + "\n--- AVALIANDO CARACTERÍSTICAS ---" + RESET);

            int totalCharacteristics = predefinedSkillIndicators.size();
            int evaluatedCount = 0;

            for (int i = 0; i < totalCharacteristics; i++) {
                SkillIndicator indicator = predefinedSkillIndicators.get(i);
                System.out.println(PURPLE + "\n--- Característica " + (i + 1) + " de " + totalCharacteristics + ": " + indicator.name + " ---" + RESET);
                System.out.println(BLUE + "Descrição: " + indicator.description + RESET);
                System.out.println(BLUE + "Categoria: " + indicator.skillCategory + RESET);

                Characteristic characteristic = Characteristic.builder()
                        .name(indicator.name)
                        .description(indicator.description)
                        .skillCategory(indicator.skillCategory)
                        .evaluation(newEvaluation)
                        .build();

                Set<Rating> ratings = new HashSet<>();
                System.out.print("Pontuação (1-5): ");
                Integer score = lerInteiro();
                if (score < 1 || score > 5) {
                    exibirErro("A pontuação deve ser entre 1 e 5. Esta característica não será salva na avaliação.");
                    continue;
                }

                Rating rating = Rating.builder()
                        .score(score)
                        .comment("")
                        .evaluator(loggedInEvaluator)
                        .characteristic(characteristic)
                        .evaluation(newEvaluation)
                        .build();
                ratings.add(rating);

                characteristic.setRatings(ratings);
                characteristics.add(characteristic);
                evaluatedCount++;

                categoryScores.merge(indicator.skillCategory, (double) score, Double::sum);
                categoryCounts.merge(indicator.skillCategory, 1, Integer::sum);

            }

            if (characteristics.isEmpty()) {
                System.out.println(YELLOW + "Nenhuma característica foi avaliada nesta sessão. Avaliação não será criada." + RESET);
                return;
            }

            newEvaluation.setCharacteristics(characteristics);

            ManagerType determinedManagerType = determineManagerType(categoryScores, categoryCounts);
            newEvaluation.setManagerType(determinedManagerType);
            System.out.println(GREEN + "\n--- Tipo de Gestor Determinado: " + determinedManagerType.getDisplayValue() + " ---" + RESET);

            exibirFeedbackGerente(determinedManagerType);

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

    private void verDetalhesAvaliacao() {
        System.out.println(CYAN + "\n--- VER DETALHES DE AVALIAÇÃO ---" + RESET);

        System.out.println(CYAN + "\n--- AVALIAÇÕES DISPONÍVEIS ---" + RESET);
        List<Evaluation> allEvaluations = evaluationService.getAllEvaluations();
        if (allEvaluations.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma avaliação encontrada no sistema." + RESET);
            return;
        } else {
            System.out.println(BLUE + "ID | Título                       | Avaliado            | Avaliador           | Data" + RESET);
            System.out.println(BLUE + "---|------------------------------|---------------------|---------------------|--------------------" + RESET);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Evaluation eval : allEvaluations) {
                String titleFormatted = eval.getTitle().length() > 25 ? eval.getTitle().substring(0, 22) + "..." : String.format("%-25s", eval.getTitle());
                String evaluatedName = eval.getEvaluated() != null ? eval.getEvaluated().getName() : "N/A";
                String evaluatorName = eval.getEvaluator() != null ? eval.getEvaluator().getName() : "N/A";
                String dateFormatted = eval.getCreatedAt() != null ? eval.getCreatedAt().format(formatter) : "N/A";

                System.out.printf("%-2d | %s | %-19s | %-19s | %s\n",
                        eval.getId(), titleFormatted,
                        evaluatedName.length() > 19 ? evaluatedName.substring(0, 16) + "..." : String.format("%-19s", evaluatedName),
                        evaluatorName.length() > 19 ? evaluatorName.substring(0, 16) + "..." : String.format("%-19s", evaluatorName),
                        dateFormatted);
            }
            System.out.println(BLUE + "--------------------------------------------------------------------------------------" + RESET);
        }

        System.out.print("\nDigite o ID da Avaliação que deseja ver: ");
        Long evaluationId = lerLongId();

        try {
            Optional<Evaluation> evaluationOpt = evaluationService.getEvaluationById(evaluationId);
            if (evaluationOpt.isPresent()) {
                Evaluation evaluation = evaluationOpt.get();
                System.out.println(PURPLE + "\n--- DETALHES DA AVALIAÇÃO ID: " + evaluation.getId() + " ---" + RESET);

                System.out.println(BLUE + "Avaliação feita por: " + (evaluation.getEvaluator() != null ? evaluation.getEvaluator().getName() : "N/A") + RESET);
                System.out.println(BLUE + "Realizada em: " + (evaluation.getCreatedAt() != null ? evaluation.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "N/A") + RESET);

                System.out.println(BLUE + "\nGerente Avaliado: " + (evaluation.getEvaluated() != null ? evaluation.getEvaluated().getName() : "N/A") + RESET);
                System.out.println(BLUE + "Tipo de Gestor Determinado: " + (evaluation.getManagerType() != null ? evaluation.getManagerType().getDisplayValue() : "N/A") + RESET);

                if (evaluation.getManagerType() != null) {
                    exibirFeedbackGerente(evaluation.getManagerType());
                } else {
                    System.out.println(YELLOW + "Tipo de Gestor não definido para esta avaliação." + RESET);
                }


            } else {
                exibirErro("Avaliação não encontrada com ID: " + evaluationId);
            }
        } catch (Exception e) {
            exibirErro("Erro ao buscar detalhes da avaliação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ManagerType determineManagerType(Map<String, Double> categoryScores, Map<String, Integer> categoryCounts) {
        String highestCategory = null;
        double maxAverage = -1.0;

        System.out.println(CYAN + "\n--- Médias de Pontuação por Categoria ---" + RESET);
        for (String categoryName : Arrays.asList("ADMINISTRATIVE", "TECHNICAL", "PERSONAL")) {
            double totalScore = categoryScores.getOrDefault(categoryName, 0.0);
            int count = categoryCounts.getOrDefault(categoryName, 0);

            if (count > 0) {
                double average = totalScore / count;
                System.out.printf("  Média de %s: %.2f\n", categoryName, average);

                if (average > maxAverage) {
                    maxAverage = average;
                    highestCategory = categoryName;
                }
            } else {
                System.out.printf("  Média de %s: N/A (nenhuma característica avaliada nesta categoria)\n", categoryName);
            }
        }

        if (highestCategory != null) {
            try {
                return ManagerType.valueOf(highestCategory.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ManagerType.OTHER;
            }
        }
        return ManagerType.OTHER;
    }

    private void exibirFeedbackGerente(ManagerType managerType) {
        System.out.println(PURPLE + "\n--- FEEDBACK ESTRATÉGICO PARA O GERENTE ---" + RESET);
        switch (managerType) {
            case ADMINISTRATIVE:
                System.out.println(BLUE + "Este gerente demonstra fortes Habilidades Administrativas." + RESET);
                System.out.println(BLUE + "Pontos Fortes: Liderança, organização de processos, gestão de recursos, e capacidade de criar um ambiente produtivo." + RESET);
                System.out.println(BLUE + "Ideal para: Funções que exigem planejamento estratégico, otimização de fluxos de trabalho, coordenação de grandes equipes e manutenção da ordem e eficiência." + RESET);
                System.out.println(BLUE + "Pode ser bom em: Gestão de projetos complexos, otimização operacional, e liderança de departamentos com foco em resultados e estrutura." + RESET);
                break;
            case TECHNICAL:
                System.out.println(BLUE + "Este gerente demonstra fortes Habilidades Técnicas." + RESET);
                System.out.println(BLUE + "Pontos Fortes: Conhecimento aprofundado na área de atuação, resolução de problemas técnicos, inovação e capacidade de ser um mentor técnico." + RESET);
                System.out.println(BLUE + "Ideal para: Ambientes que exigem expertise técnica de ponta, liderança de equipes de engenharia, P&D, ou projetos que dependem de soluções técnicas inovadoras." + RESET);
                System.out.println(BLUE + "Pode ser bom em: Desenvolvimento de produtos, arquitetura de sistemas, e liderança de equipes especializadas em tecnologia." + RESET);
                break;
            case PERSONAL:
                System.out.println(BLUE + "Este gerente demonstra fortes Habilidades Pessoais." + RESET);
                System.out.println(BLUE + "Pontos Fortes: Comunicação eficaz, sensibilidade interpessoal, motivação de equipe, promoção de reconhecimento e capacidade de construir relações colaborativas." + RESET);
                System.out.println(BLUE + "Ideal para: Funções que envolvem gestão de talentos, desenvolvimento de equipe, mediação de conflitos, e construção de uma cultura organizacional positiva e engajante." + RESET);
                System.out.println(BLUE + "Pode ser bom em: Liderança de equipes multidisciplinares, recursos humanos, e funções que exigem forte inteligência emocional e habilidades de negociação." + RESET);
                break;
            case OTHER:
            default:
                System.out.println(YELLOW + "O tipo de gestor não pôde ser claramente determinado ou se encaixa em uma categoria mista/outra." + RESET);
                System.out.println(YELLOW + "Este gerente possui uma combinação equilibrada de habilidades ou as pontuações não indicam uma predominância clara." + RESET);
                System.out.println(YELLOW + "Pode ser adaptável a diversas funções, mas um aprofundamento é necessário para identificar seus maiores pontos fortes." + RESET);
                break;
        }
        System.out.println(PURPLE + "------------------------------------------" + RESET);
    }


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
                    List<Object[]> distribution = evaluationService.getManagerTypeDistribution();
                    if (distribution.isEmpty()) {
                        System.out.println(YELLOW + "Nenhum dado de distribuição encontrado." + RESET);
                    } else {
                        distribution.forEach(row ->
                                System.out.printf("Tipo Gestor: %s, Contagem: %d\n", ((ManagerType) row[0]).getDisplayValue(), (Long) row[1]));
                    }
                    break;
                case 2:
                    System.out.println(CYAN + "\n--- MÉDIA DE PONTUAÇÃO POR TIPO DE GESTOR ---" + RESET);
                    List<Object[]> averageScores = evaluationService.getAverageScoreByManagerType();
                    if (averageScores.isEmpty()) {
                        System.out.println(YELLOW + "Nenhum dado de média por tipo de gestor encontrado." + RESET);
                    } else {
                        averageScores.forEach(row ->
                                System.out.printf("Tipo Gestor: %s, Média: %.2f\n", ((ManagerType) row[0]).getDisplayValue(), (Double) row[1]));
                    }
                    break;
                case 3:
                    System.out.println(CYAN + "\n--- RESUMOS DE AVALIAÇÕES ---" + RESET);
                    List<Map<String, Object>> summaries = evaluationService.getEvaluationSummaries();
                    if (summaries.isEmpty()) {
                        System.out.println(YELLOW + "Nenhum resumo de avaliação encontrado." + RESET);
                    } else {
                        summaries.forEach(summary -> {
                            System.out.printf("ID: %d, Título: %s, Avaliado: %s, Avaliador: %s, Média: %.2f\n",
                                    (Long) summary.get("id"), (String) summary.get("title"),
                                    (String) summary.get("evaluatedName"), (String) summary.get("evaluatorName"),
                                    (Double) summary.get("averageScore"));
                        });
                    }
                    break;
                case 4:
                    System.out.println(CYAN + "\n--- MÉDIA DE PONTUAÇÃO POR TIPO DE GESTOR E CATEGORIA DE HABILIDADE ---" + RESET);
                    List<Object[]> avgSkillCat = evaluationService.getAverageScoreByManagerTypeAndSkillCategory();
                    if (avgSkillCat.isEmpty()) {
                        System.out.println(YELLOW + "Nenhum dado de média por tipo de gestor e categoria de habilidade encontrado." + RESET);
                    } else {
                        avgSkillCat.stream()
                                .collect(Collectors.groupingBy(row -> (ManagerType) row[0]))
                                .forEach((managerType, rows) -> {
                                    System.out.println("Tipo Gestor: " + managerType.getDisplayValue());
                                    rows.forEach(row ->
                                            System.out.printf("  - Categoria: %s, Média: %.2f\n", (String) row[1], (Double) row[2]));
                                });
                    }
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (opt != 0);
    }

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