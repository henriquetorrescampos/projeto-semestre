package com.projeto_final.projeto_puc_go.Console;

import com.projeto_final.projeto_puc_go.ProjetoPucGoApplication;
import com.projeto_final.projeto_puc_go.Entity.Evaluated;
import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import com.projeto_final.projeto_puc_go.Entity.Rating;
import com.projeto_final.projeto_puc_go.Entity.ManagerType; // Importar o enum ManagerType
import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
import com.projeto_final.projeto_puc_go.Service.EvaluatorService;
import com.projeto_final.projeto_puc_go.Service.EvaluationService;
import com.projeto_final.projeto_puc_go.Service.CharacteristicService;
import com.projeto_final.projeto_puc_go.Service.RatingService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeAverageScoreDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeDistributionDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationSummaryDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationDetailDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeSkillAverageDto; // Importar o novo DTO

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final EvaluatedService evaluatedService;
    private final EvaluatorService evaluatorService;
    private final EvaluationService evaluationService;
    private final CharacteristicService characteristicService;
    private final RatingService ratingService;
    private final Scanner scanner;

    // Cores para o console
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public ConsoleApplication(EvaluatedService evaluatedService,
                              EvaluatorService evaluatorService,
                              EvaluationService evaluationService,
                              CharacteristicService characteristicService,
                              RatingService ratingService) {
        this.evaluatedService = evaluatedService;
        this.evaluatorService = evaluatorService;
        this.evaluationService = evaluationService;
        this.characteristicService = characteristicService;
        this.ratingService = ratingService;
        this.scanner = new Scanner(System.in);

    }

    public void runConsoleApplication() {
        // Toda a lógica da sua aplicação de console viria aqui
        System.out.println("Aplicação de console iniciada!");
        // Ex: menu interativo, chamadas a serviços, etc.
    }

    @Override
    public void run(String... args) {
        System.out.println(GREEN + "Iniciando o aplicativo de console do Projeto PUC-GO..." + RESET);
        exibirMenuPrincipal();
    }

    private void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println(CYAN + "\n--- MENU PRINCIPAL ---" + RESET);
            System.out.println("1. Gerenciar Avaliados");
            System.out.println("2. Gerenciar Avaliadores");
            System.out.println("3. Gerenciar Avaliações");
            System.out.println("4. Gerenciar Características");
            System.out.println("5. Gerenciar Notas (Ratings)");
            System.out.println("6. Relatórios de Avaliação");
            System.out.println("0. Sair");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    gerenciarAvaliados();
                    break;
                case 2:
                    gerenciarAvaliadores();
                    break;
                case 3:
                    gerenciarAvaliacoes();
                    break;
                case 4:
                    gerenciarCaracteristicas();
                    break;
                case 5:
                    gerenciarNotas();
                    break;
                case 6:
                    exibirMenuRelatorios();
                    break;
                case 0:
                    System.out.println(GREEN + "Saindo do aplicativo. Até mais!" + RESET);
                    break;
                default:
                    exibirErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private void exibirMenuRelatorios() {
        int opcao;
        do {
            System.out.println(CYAN + "\n--- MENU DE RELATÓRIOS ---" + RESET);
            System.out.println("1. Distribuição de Avaliações por Tipo de Gestor");
            System.out.println("2. Média de Pontuação por Tipo de Gestor");
            System.out.println("3. Resumos de Avaliações");
            System.out.println("4. Média de Pontuação por Tipo de Gestor e Categoria de Habilidade"); // Nova opção
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    exibirDistribuicaoPorTipoGestor();
                    break;
                case 2:
                    exibirMediaPorTipoGestor();
                    break;
                case 3:
                    exibirResumosAvaliacoes();
                    break;
                case 4: // Novo case
                    exibirMediaPorTipoGestorEHabilidade();
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando ao Menu Principal..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // --- Métodos de Gerenciamento de Avaliados ---
    private void gerenciarAvaliados() {
        // ... (código existente para gerenciar avaliados)
        int subOpcao;
        do {
            System.out.println(CYAN + "\n--- GERENCIAR AVALIADOS ---" + RESET);
            System.out.println("1. Cadastrar Avaliado");
            System.out.println("2. Listar Avaliados");
            System.out.println("3. Buscar Avaliado por ID");
            System.out.println("4. Atualizar Avaliado");
            System.out.println("5. Excluir Avaliado");
            System.out.println("0. Voltar");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            subOpcao = lerInteiro();

            switch (subOpcao) {
                case 1:
                    cadastrarAvaliado();
                    break;
                case 2:
                    listarAvaliados();
                    break;
                case 3:
                    buscarAvaliadoPorId();
                    break;
                case 4:
                    atualizarAvaliado();
                    break;
                case 5:
                    excluirAvaliado();
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (subOpcao != 0);
    }

    private void cadastrarAvaliado() {
        System.out.println(CYAN + "\n--- CADASTRAR AVALIADO ---" + RESET);
        System.out.print("Nome do Avaliado: ");
        String name = scanner.nextLine();
        System.out.print("Feedback (opcional, ENTER para pular): ");
        String feedback = scanner.nextLine();

        listarAvaliadores();
        System.out.print("ID do Avaliador associado: ");
        Long evaluatorId = lerLongId();

        try {
            Evaluator evaluator = evaluatorService.getEvaluatorById(evaluatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Avaliador não encontrado com ID: " + evaluatorId));

            Evaluated newEvaluated = Evaluated.builder()
                    .name(name)
                    .feedback(feedback.isEmpty() ? null : feedback)
                    .evaluator(evaluator)
                    .build();

            Evaluated savedEvaluated = evaluatedService.createEvaluated(newEvaluated);
            System.out.println(GREEN + "Avaliado cadastrado com sucesso! ID: " + savedEvaluated.getId() + RESET);
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao cadastrar avaliado: " + e.getMessage());
        }
    }

    private void listarAvaliados() {
        List<Evaluated> evaluateds = evaluatedService.getAllEvaluated();
        exibirLista("LISTA DE AVALIADOS", evaluateds);
    }

    private void buscarAvaliadoPorId() {
        System.out.print("Digite o ID do Avaliado: ");
        Long id = lerLongId();
        Optional<Evaluated> evaluated = evaluatedService.getEvaluatedById(id);
        if (evaluated.isPresent()) {
            System.out.println(CYAN + "\n--- DETALHES DO AVALIADO ---" + RESET);
            System.out.println("ID: " + evaluated.get().getId());
            System.out.println("Nome: " + evaluated.get().getName());
            System.out.println("Feedback: " + (evaluated.get().getFeedback() != null ? evaluated.get().getFeedback() : "N/A"));
            System.out.println("Avaliador: " + (evaluated.get().getEvaluator() != null ? evaluated.get().getEvaluator().getName() : "N/A"));
            System.out.println("Criado em: " + evaluated.get().getCreatedAt());
            System.out.println("Última Atualização: " + (evaluated.get().getUpdatedAt() != null ? evaluated.get().getUpdatedAt() : "N/A"));
        } else {
            exibirErro("Avaliado não encontrado.");
        }
    }

    private void atualizarAvaliado() {
        System.out.print("Digite o ID do Avaliado a ser atualizado: ");
        Long id = lerLongId();
        Optional<Evaluated> existingEvaluatedOpt = evaluatedService.getEvaluatedById(id);

        if (existingEvaluatedOpt.isPresent()) {
            Evaluated existingEvaluated = existingEvaluatedOpt.get();
            System.out.println(CYAN + "\n--- ATUALIZAR AVALIADO (ID: " + id + ") ---" + RESET);
            System.out.print("Novo Nome (" + existingEvaluated.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingEvaluated.setName(newName);
            }

            System.out.print("Novo Feedback (atual: " + (existingEvaluated.getFeedback() != null ? existingEvaluated.getFeedback() : "N/A") + ", ENTER para manter): ");
            String newFeedback = scanner.nextLine();
            if (!newFeedback.isEmpty()) {
                existingEvaluated.setFeedback(newFeedback);
            }

            listarAvaliadores();
            System.out.print("Novo ID do Avaliador (atual: " + (existingEvaluated.getEvaluator() != null ? existingEvaluated.getEvaluator().getId() : "N/A") + ", ENTER para manter): ");
            String evaluatorIdStr = scanner.nextLine();
            if (!evaluatorIdStr.isEmpty()) {
                try {
                    Long newEvaluatorId = Long.parseLong(evaluatorIdStr);
                    Evaluator newEvaluator = evaluatorService.getEvaluatorById(newEvaluatorId)
                            .orElseThrow(() -> new ResourceNotFoundException("Novo Avaliador não encontrado com ID: " + newEvaluatorId));
                    existingEvaluated.setEvaluator(newEvaluator);
                } catch (NumberFormatException e) {
                    exibirErro("ID do Avaliador inválido. Mantendo o avaliador atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo o avaliador atual.");
                }
            }

            Optional<Evaluated> updatedEvaluated = evaluatedService.updateEvaluated(id, existingEvaluated);
            if (updatedEvaluated.isPresent()) {
                System.out.println(GREEN + "Avaliado atualizado com sucesso!" + RESET);
            } else {
                exibirErro("Falha ao atualizar avaliado.");
            }
        } else {
            exibirErro("Avaliado não encontrado com ID: " + id);
        }
    }

    private void excluirAvaliado() {
        System.out.print("Digite o ID do Avaliado a ser excluído: ");
        Long id = lerLongId();
        try {
            boolean deleted = evaluatedService.deleteEvaluated(id);
            if (deleted) {
                System.out.println(GREEN + "Avaliado excluído com sucesso!" + RESET);
            } else {
                exibirErro("Avaliado não encontrado.");
            }
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao excluir avaliado: " + e.getMessage());
        }
    }

    // --- Métodos de Gerenciamento de Avaliadores ---
    private void gerenciarAvaliadores() {
        // ... (código existente para gerenciar avaliadores)
        int subOpcao;
        do {
            System.out.println(CYAN + "\n--- GERENCIAR AVALIADORES ---" + RESET);
            System.out.println("1. Cadastrar Avaliador");
            System.out.println("2. Listar Avaliadores");
            System.out.println("3. Buscar Avaliador por ID");
            System.out.println("4. Atualizar Avaliador");
            System.out.println("5. Excluir Avaliador");
            System.out.println("0. Voltar");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            subOpcao = lerInteiro();

            switch (subOpcao) {
                case 1:
                    cadastrarAvaliador();
                    break;
                case 2:
                    listarAvaliadores();
                    break;
                case 3:
                    buscarAvaliadorPorId();
                    break;
                case 4:
                    atualizarAvaliador();
                    break;
                case 5:
                    excluirAvaliador();
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (subOpcao != 0);
    }

    private void cadastrarAvaliador() {
        System.out.println(CYAN + "\n--- CADASTRAR AVALIADOR ---" + RESET);
        System.out.print("Nome do Avaliador: ");
        String name = scanner.nextLine();
        System.out.print("Email do Avaliador: ");
        String email = scanner.nextLine();

        try {
            Evaluator newEvaluator = Evaluator.builder()
                    .name(name)
                    .email(email)
                    .build();
            Evaluator savedEvaluator = evaluatorService.createEvaluator(newEvaluator);
            System.out.println(GREEN + "Avaliador cadastrado com sucesso! ID: " + savedEvaluator.getId() + RESET);
        } catch (Exception e) {
            exibirErro("Erro ao cadastrar avaliador: " + e.getMessage());
        }
    }

    private void listarAvaliadores() {
        List<Evaluator> evaluators = evaluatorService.getAllEvaluators();
        exibirLista("LISTA DE AVALIADORES", evaluators);
    }

    private void buscarAvaliadorPorId() {
        System.out.print("Digite o ID do Avaliador: ");
        Long id = lerLongId();
        Optional<Evaluator> evaluator = evaluatorService.getEvaluatorById(id);
        if (evaluator.isPresent()) {
            System.out.println(CYAN + "\n--- DETALHES DO AVALIADOR ---" + RESET);
            System.out.println("ID: " + evaluator.get().getId());
            System.out.println("Nome: " + evaluator.get().getName());
            System.out.println("Email: " + evaluator.get().getEmail());
            System.out.println("Criado em: " + evaluator.get().getCreatedAt());
            System.out.println("Última Atualização: " + (evaluator.get().getUpdatedAt() != null ? evaluator.get().getUpdatedAt() : "N/A"));
        } else {
            exibirErro("Avaliador não encontrado.");
        }
    }

    private void atualizarAvaliador() {
        System.out.print("Digite o ID do Avaliador a ser atualizado: ");
        Long id = lerLongId();
        Optional<Evaluator> existingEvaluatorOpt = evaluatorService.getEvaluatorById(id);

        if (existingEvaluatorOpt.isPresent()) {
            Evaluator existingEvaluator = existingEvaluatorOpt.get();
            System.out.println(CYAN + "\n--- ATUALIZAR AVALIADOR (ID: " + id + ") ---" + RESET);
            System.out.print("Novo Nome (" + existingEvaluator.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingEvaluator.setName(newName);
            }

            System.out.print("Novo Email (" + existingEvaluator.getEmail() + "): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                existingEvaluator.setEmail(newEmail);
            }

            Optional<Evaluator> updatedEvaluator = evaluatorService.updateEvaluator(id, existingEvaluator);
            if (updatedEvaluator.isPresent()) {
                System.out.println(GREEN + "Avaliador atualizado com sucesso!" + RESET);
            } else {
                exibirErro("Falha ao atualizar avaliador.");
            }
        } else {
            exibirErro("Avaliador não encontrado com ID: " + id);
        }
    }

    private void excluirAvaliador() {
        System.out.print("Digite o ID do Avaliador a ser excluído: ");
        Long id = lerLongId();
        try {
            boolean deleted = evaluatorService.deleteEvaluator(id);
            if (deleted) {
                System.out.println(GREEN + "Avaliador excluído com sucesso!" + RESET);
            } else {
                exibirErro("Avaliador não encontrado.");
            }
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao excluir avaliador: " + e.getMessage());
        }
    }

    // --- Métodos de Gerenciamento de Avaliações ---
    private void gerenciarAvaliacoes() {
        // ... (código existente para gerenciar avaliações)
        int subOpcao;
        do {
            System.out.println(CYAN + "\n--- GERENCIAR AVALIAÇÕES ---" + RESET);
            System.out.println("1. Criar Avaliação");
            System.out.println("2. Listar Avaliações");
            System.out.println("3. Buscar Avaliação por ID");
            System.out.println("4. Atualizar Avaliação");
            System.out.println("5. Excluir Avaliação");
            System.out.println("6. Listar Avaliações por Avaliado");
            System.out.println("7. Listar Avaliações por Avaliador");
            System.out.println("0. Voltar");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            subOpcao = lerInteiro();

            switch (subOpcao) {
                case 1:
                    criarAvaliacao();
                    break;
                case 2:
                    listarAvaliacoes();
                    break;
                case 3:
                    buscarAvaliacaoPorId();
                    break;
                case 4:
                    atualizarAvaliacao();
                    break;
                case 5:
                    excluirAvaliacao();
                    break;
                case 6:
                    listarAvaliacoesPorAvaliado();
                    break;
                case 7:
                    listarAvaliacoesPorAvaliador();
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (subOpcao != 0);
    }

    private void criarAvaliacao() {
        System.out.println(CYAN + "\n--- CRIAR AVALIAÇÃO ---" + RESET);
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        LocalDateTime startDate = null;
        while (startDate == null) {
            System.out.print("Data de Início (AAAA-MM-DD HH:MM): ");
            startDate = parseDateTime(scanner.nextLine());
        }

        LocalDateTime endDate = null;
        while (endDate == null) {
            System.out.print("Data de Fim (AAAA-MM-DD HH:MM): ");
            endDate = parseDateTime(scanner.nextLine());
        }

        listarAvaliados();
        System.out.print("ID do Avaliado: ");
        Long evaluatedId = lerLongId();

        listarAvaliadores();
        System.out.print("ID do Avaliador: ");
        Long evaluatorId = lerLongId();

        ManagerType managerType = null;
        while (managerType == null) {
            System.out.println("Selecione o Tipo de Gestor:");
            for (ManagerType type : ManagerType.values()) {
                System.out.println(type.ordinal() + 1 + ". " + type.getDisplayValue());
            }
            System.out.print("Opção: ");
            int managerTypeOption = lerInteiro();
            try {
                managerType = ManagerType.values()[managerTypeOption - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                exibirErro("Opção inválida. Por favor, escolha um número da lista.");
            }
        }


        try {
            Evaluated evaluated = evaluatedService.getEvaluatedById(evaluatedId)
                    .orElseThrow(() -> new ResourceNotFoundException("Avaliado não encontrado com ID: " + evaluatedId));
            Evaluator evaluator = evaluatorService.getEvaluatorById(evaluatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Avaliador não encontrado com ID: " + evaluatorId));

            Evaluation newEvaluation = Evaluation.builder()
                    .title(title)
                    .description(description)
                    .startDate(startDate)
                    .endDate(endDate)
                    .evaluated(evaluated)
                    .evaluator(evaluator)
                    .managerType(managerType) // Atribui o tipo de gestor
                    .build();

            // Adicionar características e ratings
            System.out.print("Deseja adicionar características agora? (s/n): ");
            String addChars = scanner.nextLine();
            if (addChars.equalsIgnoreCase("s")) {
                List<Characteristic> characteristics = new ArrayList<>();
                String addMoreChars;
                do {
                    System.out.print("Nome da Característica: ");
                    String charName = scanner.nextLine();
                    System.out.print("Descrição da Característica: ");
                    String charDescription = scanner.nextLine();

                    String skillCategory = null;
                    while (skillCategory == null || skillCategory.trim().isEmpty()) {
                        System.out.print("Categoria da Habilidade (Administrativa, Técnica, Pessoal, Outra): ");
                        skillCategory = scanner.nextLine();
                        if (skillCategory.trim().isEmpty()) {
                            exibirErro("A categoria da habilidade não pode ser vazia.");
                        }
                    }

                    Characteristic characteristic = Characteristic.builder()
                            .name(charName)
                            .description(charDescription)
                            .skillCategory(skillCategory) // Define a categoria da habilidade
                            .evaluation(newEvaluation) // Associa a característica à avaliação
                            .build();

                    List<Rating> ratings = new ArrayList<>();
                    String addMoreRatings;
                    do {
                        System.out.print("Pontuação (1-5): ");
                        Integer score = lerInteiro();
                        System.out.print("Comentário para a nota: ");
                        String comment = scanner.nextLine();

                        Rating rating = Rating.builder()
                                .score(score)
                                .comment(comment)
                                .evaluator(evaluator) // O avaliador da nota é o mesmo da avaliação
                                .characteristic(characteristic) // Associa a nota à característica
                                .evaluation(newEvaluation) // Associa a nota à avaliação
                                .build();
                        ratings.add(rating);

                        System.out.print("Adicionar mais notas para esta característica? (s/n): ");
                        addMoreRatings = scanner.nextLine();
                    } while (addMoreRatings.equalsIgnoreCase("s"));
                    characteristic.setRatings(ratings.stream().collect(Collectors.toSet())); // Converte para Set

                    characteristics.add(characteristic);

                    System.out.print("Adicionar mais características para esta avaliação? (s/n): ");
                    addMoreChars = scanner.nextLine();
                } while (addMoreChars.equalsIgnoreCase("s"));
                newEvaluation.setCharacteristics(characteristics.stream().collect(Collectors.toSet())); // Converte para Set
            }


            Evaluation savedEvaluation = evaluationService.createEvaluation(newEvaluation);
            System.out.println(GREEN + "Avaliação criada com sucesso! ID: " + savedEvaluation.getId() + RESET);

        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao criar avaliação: " + e.getMessage());
        }
    }


    private void listarAvaliacoes() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        exibirLista("LISTA DE AVALIAÇÕES", evaluations);
    }

    private void buscarAvaliacaoPorId() {
        System.out.print("Digite o ID da Avaliação: ");
        Long id = lerLongId();
        Optional<EvaluationDetailDto> detailDto = evaluationService.getEvaluationDetail(id);
        if (detailDto.isPresent()) {
            EvaluationDetailDto evaluation = detailDto.get();
            System.out.println(CYAN + "\n--- DETALHES DA AVALIAÇÃO ---" + RESET);
            System.out.println("ID: " + evaluation.getId());
            System.out.println("Título: " + evaluation.getTitle());
            System.out.println("Descrição: " + evaluation.getDescription());
            System.out.println("Data de Início: " + evaluation.getStartDate());
            System.out.println("Data de Fim: " + evaluation.getEndDate());
            System.out.println("Avaliado: " + evaluation.getEvaluatedName());
            System.out.println("Avaliador: " + evaluation.getEvaluatorName());
            System.out.println("Tipo de Gestor: " + (evaluation.getManagerType() != null ? evaluation.getManagerType().getDisplayValue() : "N/A")); // Exibe o displayValue

            System.out.println("\nCaracterísticas:");
            if (evaluation.getCharacteristics().isEmpty()) {
                System.out.println(YELLOW + "  Nenhuma característica associada." + RESET);
            } else {
                for (EvaluationDetailDto.CharacteristicDto charDto : evaluation.getCharacteristics()) {
                    System.out.println(BLUE + "  - Característica ID: " + charDto.getId() + RESET);
                    System.out.println("    Nome: " + charDto.getName());
                    System.out.println("    Descrição: " + charDto.getDescription());
                    // Adicionar skillCategory aqui se você tiver no DTO
                    // System.out.println("    Categoria: " + charDto.getSkillCategory());

                    System.out.println("    Notas:");
                    if (charDto.getRatings().isEmpty()) {
                        System.out.println(YELLOW + "      Nenhuma nota associada." + RESET);
                    } else {
                        for (EvaluationDetailDto.RatingDto ratingDto : charDto.getRatings()) {
                            System.out.println(PURPLE + "      - Nota ID: " + ratingDto.getId() + RESET);
                            System.out.println("        Pontuação: " + ratingDto.getScore());
                            System.out.println("        Comentário: " + ratingDto.getComment());
                            System.out.println("        Avaliador da Nota: " + ratingDto.getEvaluatorName());
                        }
                    }
                }
            }
        } else {
            exibirErro("Avaliação não encontrada.");
        }
    }


    private void atualizarAvaliacao() {
        System.out.print("Digite o ID da Avaliação a ser atualizada: ");
        Long id = lerLongId();
        Optional<Evaluation> existingEvaluationOpt = evaluationService.getEvaluationById(id);

        if (existingEvaluationOpt.isPresent()) {
            Evaluation existingEvaluation = existingEvaluationOpt.get();
            System.out.println(CYAN + "\n--- ATUALIZAR AVALIAÇÃO (ID: " + id + ") ---" + RESET);

            System.out.print("Novo Título (" + existingEvaluation.getTitle() + "): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                existingEvaluation.setTitle(newTitle);
            }

            System.out.print("Nova Descrição (" + existingEvaluation.getDescription() + "): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                existingEvaluation.setDescription(newDescription);
            }

            System.out.print("Nova Data de Início (atual: " + existingEvaluation.getStartDate() + ", AAAA-MM-DD HH:MM, ENTER para manter): ");
            String startDateStr = scanner.nextLine();
            if (!startDateStr.isEmpty()) {
                LocalDateTime newStartDate = parseDateTime(startDateStr);
                if (newStartDate != null) {
                    existingEvaluation.setStartDate(newStartDate);
                } else {
                    exibirErro("Formato de data/hora inválido. Mantendo data de início atual.");
                }
            }

            System.out.print("Nova Data de Fim (atual: " + existingEvaluation.getEndDate() + ", AAAA-MM-DD HH:MM, ENTER para manter): ");
            String endDateStr = scanner.nextLine();
            if (!endDateStr.isEmpty()) {
                LocalDateTime newEndDate = parseDateTime(endDateStr);
                if (newEndDate != null) {
                    existingEvaluation.setEndDate(newEndDate);
                } else {
                    exibirErro("Formato de data/hora inválido. Mantendo data de fim atual.");
                }
            }

            // Atualizar o tipo de gestor
            System.out.println("Tipo de Gestor Atual: " + (existingEvaluation.getManagerType() != null ? existingEvaluation.getManagerType().getDisplayValue() : "N/A"));
            System.out.println("Deseja alterar o Tipo de Gestor? (s/n): ");
            String changeManagerType = scanner.nextLine();
            if (changeManagerType.equalsIgnoreCase("s")) {
                ManagerType newManagerType = null;
                while (newManagerType == null) {
                    System.out.println("Selecione o Novo Tipo de Gestor:");
                    for (ManagerType type : ManagerType.values()) {
                        System.out.println(type.ordinal() + 1 + ". " + type.getDisplayValue());
                    }
                    System.out.print("Opção: ");
                    int managerTypeOption = lerInteiro();
                    try {
                        newManagerType = ManagerType.values()[managerTypeOption - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        exibirErro("Opção inválida. Por favor, escolha um número da lista.");
                    }
                }
                existingEvaluation.setManagerType(newManagerType);
            }


            listarAvaliados();
            System.out.print("Novo ID do Avaliado (atual: " + (existingEvaluation.getEvaluated() != null ? existingEvaluation.getEvaluated().getId() : "N/A") + ", ENTER para manter): ");
            String evaluatedIdStr = scanner.nextLine();
            if (!evaluatedIdStr.isEmpty()) {
                try {
                    Long newEvaluatedId = Long.parseLong(evaluatedIdStr);
                    Evaluated newEvaluated = evaluatedService.getEvaluatedById(newEvaluatedId)
                            .orElseThrow(() -> new ResourceNotFoundException("Novo Avaliado não encontrado com ID: " + newEvaluatedId));
                    existingEvaluation.setEvaluated(newEvaluated);
                } catch (NumberFormatException e) {
                    exibirErro("ID do Avaliado inválido. Mantendo o avaliado atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo o avaliado atual.");
                }
            }

            listarAvaliadores();
            System.out.print("Novo ID do Avaliador (atual: " + (existingEvaluation.getEvaluator() != null ? existingEvaluation.getEvaluator().getId() : "N/A") + ", ENTER para manter): ");
            String evaluatorIdStr = scanner.nextLine();
            if (!evaluatorIdStr.isEmpty()) {
                try {
                    Long newEvaluatorId = Long.parseLong(evaluatorIdStr);
                    Evaluator newEvaluator = evaluatorService.getEvaluatorById(newEvaluatorId)
                            .orElseThrow(() -> new ResourceNotFoundException("Novo Avaliador não encontrado com ID: " + newEvaluatorId));
                    existingEvaluation.setEvaluator(newEvaluator);
                } catch (NumberFormatException e) {
                    exibirErro("ID do Avaliador inválido. Mantendo o avaliador atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo o avaliador atual.");
                }
            }

            Optional<Evaluation> updatedEvaluation = evaluationService.updateEvaluation(id, existingEvaluation);
            if (updatedEvaluation.isPresent()) {
                System.out.println(GREEN + "Avaliação atualizada com sucesso!" + RESET);
            } else {
                exibirErro("Falha ao atualizar avaliação.");
            }
        } else {
            exibirErro("Avaliação não encontrada com ID: " + id);
        }
    }


    private void excluirAvaliacao() {
        System.out.print("Digite o ID da Avaliação a ser excluída: ");
        Long id = lerLongId();
        try {
            boolean deleted = evaluationService.deleteEvaluation(id);
            if (deleted) {
                System.out.println(GREEN + "Avaliação excluída com sucesso!" + RESET);
            } else {
                exibirErro("Avaliação não encontrada.");
            }
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao excluir avaliação: " + e.getMessage());
        }
    }

    private void listarAvaliacoesPorAvaliado() {
        listarAvaliados();
        System.out.print("Digite o ID do Avaliado para listar suas avaliações: ");
        Long evaluatedId = lerLongId();
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEvaluatedId(evaluatedId);
        if (evaluations.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma avaliação encontrada para o Avaliado com ID: " + evaluatedId + RESET);
        } else {
            exibirLista("AVALIAÇÕES DO AVALIADO (ID: " + evaluatedId + ")", evaluations);
        }
    }

    private void listarAvaliacoesPorAvaliador() {
        listarAvaliadores();
        System.out.print("Digite o ID do Avaliador para listar as avaliações que ele criou: ");
        Long evaluatorId = lerLongId();
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEvaluatorId(evaluatorId);
        if (evaluations.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma avaliação encontrada para o Avaliador com ID: " + evaluatorId + RESET);
        } else {
            exibirLista("AVALIAÇÕES CRIADAS PELO AVALIADOR (ID: " + evaluatorId + ")", evaluations);
        }
    }

    // --- Métodos de Gerenciamento de Características ---
    private void gerenciarCaracteristicas() {
        // ... (código existente para gerenciar características)
        int subOpcao;
        do {
            System.out.println(CYAN + "\n--- GERENCIAR CARACTERÍSTICAS ---" + RESET);
            System.out.println("1. Criar Característica (associada a uma Avaliação)");
            System.out.println("2. Listar Características");
            System.out.println("3. Buscar Característica por ID");
            System.out.println("4. Atualizar Característica");
            System.out.println("5. Excluir Característica");
            System.out.println("6. Listar Características por Avaliação");
            System.out.println("0. Voltar");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            subOpcao = lerInteiro();

            switch (subOpcao) {
                case 1:
                    criarCaracteristica();
                    break;
                case 2:
                    listarCaracteristicas();
                    break;
                case 3:
                    buscarCaracteristicaPorId();
                    break;
                case 4:
                    atualizarCaracteristica();
                    break;
                case 5:
                    excluirCaracteristica();
                    break;
                case 6:
                    listarCaracteristicasPorAvaliacao();
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (subOpcao != 0);
    }

    private void criarCaracteristica() {
        System.out.println(CYAN + "\n--- CRIAR CARACTERÍSTICA ---" + RESET);
        listarAvaliacoes();
        System.out.print("ID da Avaliação à qual esta característica pertence: ");
        Long evaluationId = lerLongId();

        try {
            Evaluation evaluation = evaluationService.getEvaluationById(evaluationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com ID: " + evaluationId));

            System.out.print("Nome da Característica: ");
            String name = scanner.nextLine();
            System.out.print("Descrição da Característica: ");
            String description = scanner.nextLine();
            System.out.print("Categoria da Habilidade (Administrativa, Técnica, Pessoal, Outra): "); // Solicita a categoria
            String skillCategory = scanner.nextLine();

            Characteristic newCharacteristic = Characteristic.builder()
                    .name(name)
                    .description(description)
                    .skillCategory(skillCategory) // Atribui a categoria
                    .evaluation(evaluation)
                    .build();

            Characteristic savedCharacteristic = characteristicService.createCharacteristic(newCharacteristic);
            System.out.println(GREEN + "Característica criada com sucesso! ID: " + savedCharacteristic.getId() + RESET);
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao criar característica: " + e.getMessage());
        }
    }

    private void listarCaracteristicas() {
        List<Characteristic> characteristics = characteristicService.getAllCharacteristics();
        exibirLista("LISTA DE CARACTERÍSTICAS", characteristics);
    }

    private void buscarCaracteristicaPorId() {
        System.out.print("Digite o ID da Característica: ");
        Long id = lerLongId();
        Optional<Characteristic> characteristic = characteristicService.getCharacteristicById(id);
        if (characteristic.isPresent()) {
            System.out.println(CYAN + "\n--- DETALHES DA CARACTERÍSTICA ---" + RESET);
            System.out.println("ID: " + characteristic.get().getId());
            System.out.println("Nome: " + characteristic.get().getName());
            System.out.println("Descrição: " + characteristic.get().getDescription());
            System.out.println("Categoria da Habilidade: " + characteristic.get().getSkillCategory()); // Exibe a categoria
            System.out.println("Avaliação Associada ID: " + (characteristic.get().getEvaluation() != null ? characteristic.get().getEvaluation().getId() : "N/A"));
            System.out.println("Criado em: " + characteristic.get().getCreatedAt());
            System.out.println("Última Atualização: " + (characteristic.get().getUpdatedAt() != null ? characteristic.get().getUpdatedAt() : "N/A"));
        } else {
            exibirErro("Característica não encontrada.");
        }
    }

    private void atualizarCaracteristica() {
        System.out.print("Digite o ID da Característica a ser atualizada: ");
        Long id = lerLongId();
        Optional<Characteristic> existingCharacteristicOpt = characteristicService.getCharacteristicById(id);

        if (existingCharacteristicOpt.isPresent()) {
            Characteristic existingCharacteristic = existingCharacteristicOpt.get();
            System.out.println(CYAN + "\n--- ATUALIZAR CARACTERÍSTICA (ID: " + id + ") ---" + RESET);

            System.out.print("Novo Nome (" + existingCharacteristic.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingCharacteristic.setName(newName);
            }

            System.out.print("Nova Descrição (" + existingCharacteristic.getDescription() + "): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                existingCharacteristic.setDescription(newDescription);
            }

            System.out.print("Nova Categoria da Habilidade (atual: " + existingCharacteristic.getSkillCategory() + ", ENTER para manter): ");
            String newSkillCategory = scanner.nextLine();
            if (!newSkillCategory.isEmpty()) {
                existingCharacteristic.setSkillCategory(newSkillCategory);
            }

            listarAvaliacoes();
            System.out.print("Novo ID da Avaliação (atual: " + (existingCharacteristic.getEvaluation() != null ? existingCharacteristic.getEvaluation().getId() : "N/A") + ", ENTER para manter): ");
            String evaluationIdStr = scanner.nextLine();
            if (!evaluationIdStr.isEmpty()) {
                try {
                    Long newEvaluationId = Long.parseLong(evaluationIdStr);
                    Evaluation newEvaluation = evaluationService.getEvaluationById(newEvaluationId)
                            .orElseThrow(() -> new ResourceNotFoundException("Nova Avaliação não encontrada com ID: " + newEvaluationId));
                    existingCharacteristic.setEvaluation(newEvaluation);
                } catch (NumberFormatException e) {
                    exibirErro("ID da Avaliação inválido. Mantendo a avaliação atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo a avaliação atual.");
                }
            }

            Optional<Characteristic> updatedCharacteristic = characteristicService.updateCharacteristic(id, existingCharacteristic);
            if (updatedCharacteristic.isPresent()) {
                System.out.println(GREEN + "Característica atualizada com sucesso!" + RESET);
            } else {
                exibirErro("Falha ao atualizar característica.");
            }
        } else {
            exibirErro("Característica não encontrada com ID: " + id);
        }
    }


    private void excluirCaracteristica() {
        System.out.print("Digite o ID da Característica a ser excluída: ");
        Long id = lerLongId();
        try {
            boolean deleted = characteristicService.deleteCharacteristic(id);
            if (deleted) {
                System.out.println(GREEN + "Característica excluída com sucesso!" + RESET);
            } else {
                exibirErro("Característica não encontrada.");
            }
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao excluir característica: " + e.getMessage());
        }
    }

    private void listarCaracteristicasPorAvaliacao() {
        listarAvaliacoes();
        System.out.print("Digite o ID da Avaliação para listar suas características: ");
        Long evaluationId = lerLongId();
        List<Characteristic> characteristics = characteristicService.getCharacteristicsByEvaluationId(evaluationId);
        if (characteristics.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma característica encontrada para a Avaliação com ID: " + evaluationId + RESET);
        } else {
            exibirLista("CARACTERÍSTICAS DA AVALIAÇÃO (ID: " + evaluationId + ")", characteristics);
        }
    }

    // --- Métodos de Gerenciamento de Notas (Ratings) ---
    private void gerenciarNotas() {
        // ... (código existente para gerenciar notas)
        int subOpcao;
        do {
            System.out.println(CYAN + "\n--- GERENCIAR NOTAS (RATINGS) ---" + RESET);
            System.out.println("1. Criar Nota");
            System.out.println("2. Listar Notas");
            System.out.println("3. Buscar Nota por ID");
            System.out.println("4. Atualizar Nota");
            System.out.println("5. Excluir Nota");
            System.out.println("6. Listar Notas por Característica");
            System.out.println("7. Listar Notas por Avaliador");
            System.out.println("8. Listar Notas por Avaliação");
            System.out.println("0. Voltar");
            System.out.print(YELLOW + "Escolha uma opção: " + RESET);
            subOpcao = lerInteiro();

            switch (subOpcao) {
                case 1:
                    criarNota();
                    break;
                case 2:
                    listarNotas();
                    break;
                case 3:
                    buscarNotaPorId();
                    break;
                case 4:
                    atualizarNota();
                    break;
                case 5:
                    excluirNota();
                    break;
                case 6:
                    listarNotasPorCaracteristica();
                    break;
                case 7:
                    listarNotasPorAvaliador();
                    break;
                case 8:
                    listarNotasPorAvaliacao();
                    break;
                case 0:
                    System.out.println(GREEN + "Voltando..." + RESET);
                    break;
                default:
                    exibirErro("Opção inválida.");
            }
        } while (subOpcao != 0);
    }

    private void criarNota() {
        System.out.println(CYAN + "\n--- CRIAR NOTA ---" + RESET);
        listarCaracteristicas();
        System.out.print("ID da Característica à qual esta nota pertence: ");
        Long characteristicId = lerLongId();

        listarAvaliadores();
        System.out.print("ID do Avaliador que está dando esta nota: ");
        Long evaluatorId = lerLongId();

        listarAvaliacoes(); // Necessário para associar a nota à avaliação
        System.out.print("ID da Avaliação principal à qual esta nota está vinculada: ");
        Long evaluationId = lerLongId();

        try {
            Characteristic characteristic = characteristicService.getCharacteristicById(characteristicId)
                    .orElseThrow(() -> new ResourceNotFoundException("Característica não encontrada com ID: " + characteristicId));
            Evaluator evaluator = evaluatorService.getEvaluatorById(evaluatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Avaliador não encontrado com ID: " + evaluatorId));
            Evaluation evaluation = evaluationService.getEvaluationById(evaluationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com ID: " + evaluationId));


            System.out.print("Pontuação (1-5): ");
            Integer score = lerInteiro();
            if (score < 1 || score > 5) {
                throw new IllegalArgumentException("A pontuação deve ser entre 1 e 5.");
            }
            System.out.print("Comentário para a nota: ");
            String comment = scanner.nextLine();

            Rating newRating = Rating.builder()
                    .score(score)
                    .comment(comment)
                    .characteristic(characteristic)
                    .evaluator(evaluator)
                    .evaluation(evaluation) // Associa a nota à avaliação principal
                    .build();

            Rating savedRating = ratingService.createRating(newRating);
            System.out.println(GREEN + "Nota criada com sucesso! ID: " + savedRating.getId() + RESET);
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (IllegalArgumentException e) {
            exibirErro("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao criar nota: " + e.getMessage());
        }
    }

    private void listarNotas() {
        List<Rating> ratings = ratingService.getAllRatings();
        exibirLista("LISTA DE NOTAS", ratings);
    }

    private void buscarNotaPorId() {
        System.out.print("Digite o ID da Nota: ");
        Long id = lerLongId();
        Optional<Rating> rating = ratingService.getRatingById(id);
        if (rating.isPresent()) {
            System.out.println(CYAN + "\n--- DETALHES DA NOTA ---" + RESET);
            System.out.println("ID: " + rating.get().getId());
            System.out.println("Pontuação: " + rating.get().getScore());
            System.out.println("Comentário: " + rating.get().getComment());
            System.out.println("Característica Associada ID: " + (rating.get().getCharacteristic() != null ? rating.get().getCharacteristic().getId() : "N/A"));
            System.out.println("Avaliador da Nota ID: " + (rating.get().getEvaluator() != null ? rating.get().getEvaluator().getId() : "N/A"));
            System.out.println("Avaliação Principal ID: " + (rating.get().getEvaluation() != null ? rating.get().getEvaluation().getId() : "N/A"));
            System.out.println("Criado em: " + rating.get().getCreatedAt());
            System.out.println("Última Atualização: " + (rating.get().getUpdatedAt() != null ? rating.get().getUpdatedAt() : "N/A"));
        } else {
            exibirErro("Nota não encontrada.");
        }
    }

    private void atualizarNota() {
        System.out.print("Digite o ID da Nota a ser atualizada: ");
        Long id = lerLongId();
        Optional<Rating> existingRatingOpt = ratingService.getRatingById(id);

        if (existingRatingOpt.isPresent()) {
            Rating existingRating = existingRatingOpt.get();
            System.out.println(CYAN + "\n--- ATUALIZAR NOTA (ID: " + id + ") ---" + RESET);

            System.out.print("Nova Pontuação (atual: " + existingRating.getScore() + ", 1-5, ENTER para manter): ");
            String newScoreStr = scanner.nextLine();
            if (!newScoreStr.isEmpty()) {
                try {
                    Integer newScore = Integer.parseInt(newScoreStr);
                    if (newScore < 1 || newScore > 5) {
                        exibirErro("A pontuação deve ser entre 1 e 5. Mantendo pontuação atual.");
                    } else {
                        existingRating.setScore(newScore);
                    }
                } catch (NumberFormatException e) {
                    exibirErro("Pontuação inválida. Mantendo pontuação atual.");
                }
            }

            System.out.print("Novo Comentário (atual: " + existingRating.getComment() + ", ENTER para manter): ");
            String newComment = scanner.nextLine();
            if (!newComment.isEmpty()) {
                existingRating.setComment(newComment);
            }

            listarCaracteristicas();
            System.out.print("Novo ID da Característica (atual: " + (existingRating.getCharacteristic() != null ? existingRating.getCharacteristic().getId() : "N/A") + ", ENTER para manter): ");
            String characteristicIdStr = scanner.nextLine();
            if (!characteristicIdStr.isEmpty()) {
                try {
                    Long newCharacteristicId = Long.parseLong(characteristicIdStr);
                    Characteristic newCharacteristic = characteristicService.getCharacteristicById(newCharacteristicId)
                            .orElseThrow(() -> new ResourceNotFoundException("Nova Característica não encontrada com ID: " + newCharacteristicId));
                    existingRating.setCharacteristic(newCharacteristic);
                } catch (NumberFormatException e) {
                    exibirErro("ID da Característica inválido. Mantendo a característica atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo a característica atual.");
                }
            }

            listarAvaliadores();
            System.out.print("Novo ID do Avaliador (atual: " + (existingRating.getEvaluator() != null ? existingRating.getEvaluator().getId() : "N/A") + ", ENTER para manter): ");
            String evaluatorIdStr = scanner.nextLine();
            if (!evaluatorIdStr.isEmpty()) {
                try {
                    Long newEvaluatorId = Long.parseLong(evaluatorIdStr);
                    Evaluator newEvaluator = evaluatorService.getEvaluatorById(newEvaluatorId)
                            .orElseThrow(() -> new ResourceNotFoundException("Novo Avaliador não encontrado com ID: " + newEvaluatorId));
                    existingRating.setEvaluator(newEvaluator);
                } catch (NumberFormatException e) {
                    exibirErro("ID do Avaliador inválido. Mantendo o avaliador atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo o avaliador atual.");
                }
            }

            listarAvaliacoes();
            System.out.print("Novo ID da Avaliação Principal (atual: " + (existingRating.getEvaluation() != null ? existingRating.getEvaluation().getId() : "N/A") + ", ENTER para manter): ");
            String evaluationIdStr = scanner.nextLine();
            if (!evaluationIdStr.isEmpty()) {
                try {
                    Long newEvaluationId = Long.parseLong(evaluationIdStr);
                    Evaluation newEvaluation = evaluationService.getEvaluationById(newEvaluationId)
                            .orElseThrow(() -> new ResourceNotFoundException("Nova Avaliação não encontrada com ID: " + newEvaluationId));
                    existingRating.setEvaluation(newEvaluation);
                } catch (NumberFormatException e) {
                    exibirErro("ID da Avaliação inválido. Mantendo a avaliação principal atual.");
                } catch (ResourceNotFoundException e) {
                    exibirErro(e.getMessage() + ". Mantendo a avaliação principal atual.");
                }
            }

            Optional<Rating> updatedRating = ratingService.updateRating(id, existingRating);
            if (updatedRating.isPresent()) {
                System.out.println(GREEN + "Nota atualizada com sucesso!" + RESET);
            } else {
                exibirErro("Falha ao atualizar nota.");
            }
        } else {
            exibirErro("Nota não encontrada com ID: " + id);
        }
    }


    private void excluirNota() {
        System.out.print("Digite o ID da Nota a ser excluída: ");
        Long id = lerLongId();
        try {
            boolean deleted = ratingService.deleteRating(id);
            if (deleted) {
                System.out.println(GREEN + "Nota excluída com sucesso!" + RESET);
            } else {
                exibirErro("Nota não encontrada.");
            }
        } catch (ResourceNotFoundException e) {
            exibirErro(e.getMessage());
        } catch (Exception e) {
            exibirErro("Erro ao excluir nota: " + e.getMessage());
        }
    }

    private void listarNotasPorCaracteristica() {
        listarCaracteristicas();
        System.out.print("Digite o ID da Característica para listar suas notas: ");
        Long characteristicId = lerLongId();
        List<Rating> ratings = ratingService.getRatingsByCharacteristicId(characteristicId);
        if (ratings.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma nota encontrada para a Característica com ID: " + characteristicId + RESET);
        } else {
            exibirLista("NOTAS DA CARACTERÍSTICA (ID: " + characteristicId + ")", ratings);
        }
    }

    private void listarNotasPorAvaliador() {
        listarAvaliadores();
        System.out.print("Digite o ID do Avaliador para listar as notas que ele deu: ");
        Long evaluatorId = lerLongId();
        List<Rating> ratings = ratingService.getRatingsByEvaluatorId(evaluatorId);
        if (ratings.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma nota encontrada para o Avaliador com ID: " + evaluatorId + RESET);
        } else {
            exibirLista("NOTAS DADAS PELO AVALIADOR (ID: " + evaluatorId + ")", ratings);
        }
    }

    private void listarNotasPorAvaliacao() {
        listarAvaliacoes();
        System.out.print("Digite o ID da Avaliação para listar todas as notas vinculadas a ela: ");
        Long evaluationId = lerLongId();
        List<Rating> ratings = ratingService.getRatingsByEvaluationId(evaluationId);
        if (ratings.isEmpty()) {
            System.out.println(YELLOW + "Nenhuma nota encontrada para a Avaliação com ID: " + evaluationId + RESET);
        } else {
            exibirLista("NOTAS DA AVALIAÇÃO (ID: " + evaluationId + ")", ratings);
        }
    }


    // --- Métodos de Relatórios ---
    private void exibirDistribuicaoPorTipoGestor() {
        System.out.println(CYAN + "\n--- DISTRIBUIÇÃO DE AVALIAÇÕES POR TIPO DE GESTOR ---" + RESET);
        List<ManagerTypeDistributionDto> distribution = evaluationService.getManagerTypeDistribution();
        if (distribution.isEmpty()) {
            System.out.println(YELLOW + "Nenhum dado de distribuição encontrado." + RESET);
        } else {
            distribution.forEach(dto ->
                    System.out.println(BLUE + "Tipo de Gestor: " + dto.getManagerType().getDisplayValue() + ", Contagem: " + dto.getCount() + RESET));
        }
    }

    private void exibirMediaPorTipoGestor() {
        System.out.println(CYAN + "\n--- MÉDIA DE PONTUAÇÃO POR TIPO DE GESTOR ---" + RESET);
        List<ManagerTypeAverageScoreDto> averageScores = evaluationService.getAverageScoreByManagerType();
        if (averageScores.isEmpty()) {
            System.out.println(YELLOW + "Nenhum dado de média por tipo de gestor encontrado." + RESET);
        } else {
            averageScores.forEach(dto ->
                    System.out.printf(BLUE + "Tipo de Gestor: %s, Média de Pontuação: %.2f%s\n",
                            dto.getManagerType().getDisplayValue(), dto.getAverageScore(), RESET));
        }
    }

    private void exibirResumosAvaliacoes() {
        System.out.println(CYAN + "\n--- RESUMOS DE AVALIAÇÕES ---" + RESET);
        List<EvaluationSummaryDto> summaries = evaluationService.getEvaluationSummaries();
        if (summaries.isEmpty()) {
            System.out.println(YELLOW + "Nenhum resumo de avaliação encontrado." + RESET);
        } else {
            summaries.forEach(summary -> {
                System.out.println(BLUE + "ID da Avaliação: " + summary.getId() + RESET);
                System.out.println("  Título: " + summary.getTitle());
                System.out.println("  Período: " + summary.getStartDate() + " a " + summary.getEndDate());
                System.out.println("  Avaliado: " + summary.getEvaluatedName());
                System.out.println("  Avaliador: " + summary.getEvaluatorName());
                System.out.printf("  Média de Pontuação: %.2f\n", summary.getAverageScore());
                System.out.println("--------------------");
            });
        }
    }

    // NOVO MÉTODO: Exibir Média de Pontuação por Tipo de Gestor e Categoria de Habilidade
    private void exibirMediaPorTipoGestorEHabilidade() {
        System.out.println(CYAN + "\n--- MÉDIA DE PONTUAÇÃO POR TIPO DE GESTOR E CATEGORIA DE HABILIDADE ---" + RESET);
        List<ManagerTypeSkillAverageDto> averageScores = evaluationService.getAverageScoreByManagerTypeAndSkillCategory();
        if (averageScores.isEmpty()) {
            System.out.println(YELLOW + "Nenhum dado de média por tipo de gestor e categoria de habilidade encontrado." + RESET);
        } else {
            // Agrupar os resultados para uma melhor visualização no console
            averageScores.stream()
                    .collect(Collectors.groupingBy(ManagerTypeSkillAverageDto::getManagerType))
                    .forEach((managerType, skills) -> {
                        System.out.println(BLUE + "Tipo de Gestor: " + managerType.getDisplayValue() + RESET);
                        skills.forEach(skill ->
                                System.out.printf("  - Categoria: %s, Média de Pontuação: %.2f\n",
                                        skill.getSkillCategory(), skill.getAverageScore()));
                        System.out.println("--------------------");
                    });
        }
    }


    // --- Métodos Auxiliares ---
    private Long lerLongId() {
        while (!scanner.hasNextLong()) {
            System.out.println(RED + "Entrada inválida. Por favor, digite um número inteiro para o ID." + RESET);
            scanner.next();
        }
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir a nova linha
        return id;
    }

    private Integer lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.println(RED + "Entrada inválida. Por favor, digite um número inteiro." + RESET);
            scanner.next();
        }
        Integer valor = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        return valor;
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            exibirErro("Formato de data/hora inválido. Use AAAA-MM-DD HH:MM. Ex: 2025-01-01 10:30");
            return null;
        }
    }

    private <T> void exibirLista(String titulo, List<T> lista) {
        System.out.println(CYAN + "\n--- " + titulo + " ---" + RESET);
        if (lista.isEmpty()) {
            System.out.println(YELLOW + "Nenhum item encontrado." + RESET);
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void exibirErro(String mensagem) {
        System.err.println(RED + "ERRO: " + mensagem + RESET);
    }
}