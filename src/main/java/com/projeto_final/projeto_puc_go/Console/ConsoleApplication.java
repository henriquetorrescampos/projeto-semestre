// src/main/java/com/projeto_final/projeto_puc_go/console/ConsoleApplication.java
package com.projeto_final.projeto_puc_go.Console;

import com.projeto_final.projeto_puc_go.ProjetoPucGoApplication;
import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
// Importe os outros Services conforme você os implementar:
// import com.projeto_final.projeto_puc_go.Service.AnswerService;
// import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
// import com.projeto_final.projeto_puc_go.Service.EvaluatorService;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApplication {

    // Instâncias dos seus serviços (serão injetadas pelo Spring)
    private static QuestionService questionService;
    // Adicione os outros services aqui quando estiverem prontos
    // private static AnswerService answerService;
    // private static EvaluatedService evaluatedService;
    // private static EvaluatorService evaluatorService;

    private static Scanner scanner; // Objeto Scanner para leitura de entrada

    public static void main(String[] args) {
        // PASSO 1: Inicializa o contexto do Spring Boot
        // Isso faz com que seus Services, Repositories, etc., sejam criados e gerenciados pelo Spring
        ConfigurableApplicationContext context = SpringApplication.run(ProjetoPucGoApplication.class, args);

        // PASSO 2: Obtém as instâncias dos seus Services do contexto Spring
        // O Spring 'injeta' os objetos aqui
        questionService = context.getBean(QuestionService.class);
        // Descomente e adicione os outros serviços quando criá-los:
        // answerService = context.getBean(AnswerService.class);
        // evaluatedService = context.getBean(EvaluatedService.class);
        // evaluatorService = context.getBean(EvaluatorService.class);


        scanner = new Scanner(System.in); // Inicializa o Scanner
        int opcao;

        do {
            exibirMenuPrincipal(); // Mostra o menu
            opcao = lerOpcao();   // Lê a opção do usuário

            switch (opcao) {
                case 1:
                    menuQuestoes(); // Chama o submenu de Questões
                    break;
                case 2:
                    System.out.println("Funcionalidade de Respostas em desenvolvimento.");
                    // Chame o menu de respostas aqui: menuRespostas();
                    break;
                case 3:
                    System.out.println("Funcionalidade de Avaliados em desenvolvimento.");
                    // Chame o menu de avaliados aqui: menuAvaliados();
                    break;
                case 4:
                    System.out.println("Funcionalidade de Avaliadores em desenvolvimento.");
                    // Chame o menu de avaliadores aqui: menuAvaliadores();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            // Espera o usuário pressionar Enter para continuar, após cada operação
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine(); // Consome a quebra de linha pendente após a opção numérica
            scanner.nextLine(); // Espera a entrada do Enter

        } while (opcao != 0); // Repete o menu até o usuário escolher sair (0)

        scanner.close(); // Fecha o Scanner
        context.close(); // Fecha o contexto Spring (importante para liberar recursos)
    }

    // --- Métodos de UI (Menu Principal) ---
    private static void exibirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Gerenciar Questões");
        System.out.println("2. Gerenciar Respostas");
        System.out.println("3. Gerenciar Avaliados");
        System.out.println("4. Gerenciar Avaliadores");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Método auxiliar para ler opções numéricas com tratamento de erro
    private static int lerOpcao() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.next(); // Consome a entrada inválida para evitar loop infinito
            return -1; // Retorna um valor inválido para que o switch-case trate
        } finally {
            // Este scanner.nextLine() é crucial para consumir a quebra de linha
            // que sobra após nextInt(), nextLong(), etc., e que pode causar problemas
            // na próxima leitura de String (nextLine()).
            scanner.nextLine();
        }
    }

    // --- Submenu e Funções para Gerenciar Questões ---
    private static void menuQuestoes() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Questões ---");
            System.out.println("1. Cadastrar Questão");
            System.out.println("2. Listar Todas as Questões");
            System.out.println("3. Buscar Questão por ID");
            System.out.println("4. Atualizar Questão");
            System.out.println("5. Excluir Questão");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            subOpcao = lerOpcao();

            switch (subOpcao) {
                case 1:
                    cadastrarQuestao();
                    break;
                case 2:
                    listarTodasQuestoes();
                    break;
                case 3:
                    buscarQuestaoPorId();
                    break;
                case 4:
                    atualizarQuestao();
                    break;
                case 5:
                    excluirQuestao();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (subOpcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine(); // Espera o Enter após a operação no submenu
            }
        } while (subOpcao != 0);
    }

    private static void cadastrarQuestao() {
        System.out.println("\n--- Cadastrar Nova Questão ---");
        System.out.print("Digite o conteúdo da questão: ");
        String content = scanner.nextLine();

        // Validação simples de entrada via console
        if (content.trim().isEmpty()) {
            System.out.println("Erro: O conteúdo da questão não pode ser vazio.");
            return; // Sai da função se a entrada for inválida
        }

        Question novaQuestao = Question.builder().content(content).build();
        try {
            Question criada = questionService.createQuestion(novaQuestao);
            System.out.println("Questão cadastrada com sucesso! ID: " + criada.getId());
        } catch (Exception e) {
            // Captura qualquer erro do Service (ex: de banco de dados)
            System.err.println("Erro ao cadastrar questão: " + e.getMessage());
        }
    }

    private static void listarTodasQuestoes() {
        System.out.println("\n--- Lista de Questões ---");
        List<Question> questoes = questionService.getAllQuestions();
        if (questoes.isEmpty()) {
            System.out.println("Nenhuma questão cadastrada.");
            return;
        }
        questoes.forEach(q -> System.out.println("ID: " + q.getId() + " - Conteúdo: " + q.getContent()));
    }

    private static void buscarQuestaoPorId() {
        System.out.print("Digite o ID da questão a buscar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine(); // Consumir nova linha
        }


        Optional<Question> questao = questionService.getQuestionById(id);
        if (questao.isPresent()) {
            System.out.println("Questão encontrada: ID: " + questao.get().getId() + " - Conteúdo: " + questao.get().getContent());
        } else {
            System.out.println("Questão com ID " + id + " não encontrada.");
        }
    }

    private static void atualizarQuestao() {
        System.out.print("Digite o ID da questão a atualizar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine(); // Consumir nova linha
        }

        Optional<Question> existingQuestion = questionService.getQuestionById(id);
        if (existingQuestion.isEmpty()) {
            System.out.println("Questão com ID " + id + " não encontrada para atualização.");
            return;
        }

        System.out.print("Digite o novo conteúdo da questão (atual: " + existingQuestion.get().getContent() + "): ");
        String newContent = scanner.nextLine();

        if (newContent.trim().isEmpty()) {
            System.out.println("Erro: O novo conteúdo da questão não pode ser vazio.");
            return;
        }

        // Criar uma nova instância de Question apenas com o que será atualizado
        Question updatedDetails = Question.builder().content(newContent).build();
        try {
            Optional<Question> updated = questionService.updateQuestion(id, updatedDetails);
            if (updated.isPresent()) {
                System.out.println("Questão atualizada com sucesso!");
            } else {
                // Se o service não lançar exceção e retornar Optional.empty()
                System.out.println("Falha ao atualizar questão. Possivelmente ID não encontrado (mas o service deveria lançar erro aqui).");
            }
        } catch (Exception e) { // Captura exceções do service, como ResourceNotFoundException
            System.err.println("Erro ao atualizar questão: " + e.getMessage());
        }
    }

    private static void excluirQuestao() {
        System.out.print("Digite o ID da questão a excluir: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine(); // Consumir nova linha
        }

        try {
            boolean deleted = questionService.deleteQuestion(id);
            if (deleted) {
                System.out.println("Questão excluída com sucesso!");
            } else {
                // Isso será alcançado se o serviço retornar 'false' e não lançar uma exceção.
                // Se o seu service lançar ResourceNotFoundException, ele será capturado no catch.
                System.out.println("Questão com ID " + id + " não encontrada para exclusão.");
            }
        } catch (Exception e) { // Captura exceções do service, como ResourceNotFoundException
            System.err.println("Erro ao excluir questão: " + e.getMessage());
        }
    }

    // --- Métodos de menu para outras entidades (você vai completar) ---
    // public static void menuRespostas() { /* ... */ }
    // public static void menuAvaliados() { /* ... */ }
    // public static void menuAvaliadores() { /* ... */ }
}