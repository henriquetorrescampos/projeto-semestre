// src/main/java/com/projeto_final/projeto_puc_go/console/ConsoleApplication.java
package com.projeto_final.projeto_puc_go.Console;

import com.projeto_final.projeto_puc_go.ProjetoPucGoApplication;
import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Entity.Answer;
import com.projeto_final.projeto_puc_go.Entity.Evaluated;
import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import com.projeto_final.projeto_puc_go.Service.AnswerService;
import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
import com.projeto_final.projeto_puc_go.Service.EvaluatorService;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApplication {

    private static QuestionService questionService;
    private static AnswerService answerService;
    private static EvaluatedService evaluatedService;
    private static EvaluatorService evaluatorService;

    private static Scanner scanner;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProjetoPucGoApplication.class, args);

        questionService = context.getBean(QuestionService.class);
        answerService = context.getBean(AnswerService.class);
        evaluatedService = context.getBean(EvaluatedService.class);
        evaluatorService = context.getBean(EvaluatorService.class);

        scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuQuestoes();
                    break;
                case 2:
                    menuRespostas();
                    break;
                case 3:
                    menuAvaliados();
                    break;
                case 4:
                    menuAvaliadores();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
            scanner.nextLine();

        } while (opcao != 0);

        scanner.close();
        context.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Gerenciar Questões");
        System.out.println("2. Gerenciar Respostas");
        System.out.println("3. Gerenciar Avaliados");
        System.out.println("4. Gerenciar Avaliadores");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.next();
            return -1;
        } finally {
            scanner.nextLine();
        }
    }

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
                scanner.nextLine();
            }
        } while (subOpcao != 0);
    }

    private static void cadastrarQuestao() {
        System.out.println("\n--- Cadastrar Nova Questão ---");
        System.out.print("Digite o conteúdo da questão: ");
        String content = scanner.nextLine();

        if (content.trim().isEmpty()) {
            System.out.println("Erro: O conteúdo da questão não pode ser vazio.");
            return;
        }

        Question novaQuestao = Question.builder().content(content).build();
        try {
            Question criada = questionService.createQuestion(novaQuestao);
            System.out.println("Questão cadastrada com sucesso! ID: " + criada.getId());
        } catch (Exception e) {
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
            scanner.nextLine();
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
            scanner.nextLine();
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

        Question updatedDetails = Question.builder().content(newContent).build();
        try {
            Optional<Question> updated = questionService.updateQuestion(id, updatedDetails);
            if (updated.isPresent()) {
                System.out.println("Questão atualizada com sucesso!");
            } else {
                System.out.println("Falha ao atualizar questão.");
            }
        } catch (Exception e) {
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
            scanner.nextLine();
        }

        try {
            boolean deleted = questionService.deleteQuestion(id);
            if (deleted) {
                System.out.println("Questão excluída com sucesso!");
            } else {
                System.out.println("Questão com ID " + id + " não encontrada para exclusão.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir questão: " + e.getMessage());
        }
    }

    private static void menuRespostas() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Respostas ---");
            System.out.println("1. Cadastrar Resposta");
            System.out.println("2. Listar Todas as Respostas");
            System.out.println("3. Buscar Resposta por ID");
            System.out.println("4. Atualizar Resposta");
            System.out.println("5. Excluir Resposta");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            subOpcao = lerOpcao();

            switch (subOpcao) {
                case 1:
                    cadastrarResposta();
                    break;
                case 2:
                    listarTodasRespostas();
                    break;
                case 3:
                    buscarRespostaPorId();
                    break;
                case 4:
                    atualizarResposta();
                    break;
                case 5:
                    excluirResposta();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (subOpcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (subOpcao != 0);
    }

    private static void cadastrarResposta() {
        System.out.println("\n--- Cadastrar Nova Resposta ---");
        System.out.print("Digite o conteúdo da resposta: ");
        String content = scanner.nextLine();

        if (content.trim().isEmpty()) {
            System.out.println("Erro: O conteúdo da resposta não pode ser vazio.");
            return;
        }

        Answer novaResposta = Answer.builder().content(content).build();
        try {
            Answer criada = answerService.createAnswer(novaResposta);
            System.out.println("Resposta cadastrada com sucesso! ID: " + criada.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar resposta: " + e.getMessage());
        }
    }

    private static void listarTodasRespostas() {
        System.out.println("\n--- Lista de Respostas ---");
        List<Answer> respostas = answerService.getAllAnswers();
        if (respostas.isEmpty()) {
            System.out.println("Nenhuma resposta cadastrada.");
            return;
        }
        respostas.forEach(r -> System.out.println("ID: " + r.getId() + " - Conteúdo: " + r.getContent()));
    }

    private static void buscarRespostaPorId() {
        System.out.print("Digite o ID da resposta a buscar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        Optional<Answer> resposta = answerService.getAnswerById(id);
        if (resposta.isPresent()) {
            System.out.println("Resposta encontrada: ID: " + resposta.get().getId() + " - Conteúdo: " + resposta.get().getContent());
        } else {
            System.out.println("Resposta com ID " + id + " não encontrada.");
        }
    }

    private static void atualizarResposta() {
        System.out.print("Digite o ID da resposta a atualizar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        Optional<Answer> existingAnswer = answerService.getAnswerById(id);
        if (existingAnswer.isEmpty()) {
            System.out.println("Resposta com ID " + id + " não encontrada para atualização.");
            return;
        }

        System.out.print("Digite o novo conteúdo da resposta (atual: " + existingAnswer.get().getContent() + "): ");
        String newContent = scanner.nextLine();

        if (newContent.trim().isEmpty()) {
            System.out.println("Erro: O novo conteúdo da resposta não pode ser vazio.");
            return;
        }

        Answer updatedDetails = Answer.builder().content(newContent).build();
        try {
            Optional<Answer> updated = answerService.updateAnswer(id, updatedDetails);
            if (updated.isPresent()) {
                System.out.println("Resposta atualizada com sucesso!");
            } else {
                System.out.println("Falha ao atualizar resposta.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar resposta: " + e.getMessage());
        }
    }

    private static void excluirResposta() {
        System.out.print("Digite o ID da resposta a excluir: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        try {
            boolean deleted = answerService.deleteAnswer(id);
            if (deleted) {
                System.out.println("Resposta excluída com sucesso!");
            } else {
                System.out.println("Resposta com ID " + id + " não encontrada para exclusão.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir resposta: " + e.getMessage());
        }
    }

    private static void menuAvaliados() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Avaliados ---");
            System.out.println("1. Cadastrar Avaliado");
            System.out.println("2. Listar Todos os Avaliados");
            System.out.println("3. Buscar Avaliado por ID");
            System.out.println("4. Atualizar Avaliado");
            System.out.println("5. Excluir Avaliado");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            subOpcao = lerOpcao();

            switch (subOpcao) {
                case 1:
                    cadastrarAvaliado();
                    break;
                case 2:
                    listarTodosAvaliados();
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
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (subOpcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (subOpcao != 0);
    }

    private static void cadastrarAvaliado() {
        System.out.println("\n--- Cadastrar Novo Avaliado ---");
        System.out.print("Digite o nome do avaliado: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Erro: O nome do avaliado não pode ser vazio.");
            return;
        }

        Evaluated novoAvaliado = Evaluated.builder().name(name).build();
        try {
            Evaluated criado = evaluatedService.createEvaluated(novoAvaliado);
            System.out.println("Avaliado cadastrado com sucesso! ID: " + criado.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar avaliado: " + e.getMessage());
        }
    }

    private static void listarTodosAvaliados() {
        System.out.println("\n--- Lista de Avaliados ---");
        // CORREÇÃO: Mude de getAllEvaluated() para getAllEvaluateds()
        List<Evaluated> avaliados = evaluatedService.getAllEvaluated();
        if (avaliados.isEmpty()) {
            System.out.println("Nenhum avaliado cadastrado.");
            return;
        }
        avaliados.forEach(a -> System.out.println("ID: " + a.getId() + " - Nome: " + a.getName()));
    }

    private static void buscarAvaliadoPorId() {
        System.out.print("Digite o ID do avaliado a buscar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        Optional<Evaluated> avaliado = evaluatedService.getEvaluatedById(id);
        if (avaliado.isPresent()) {
            System.out.println("Avaliado encontrado: ID: " + avaliado.get().getId() + " - Nome: " + avaliado.get().getName());
        } else {
            System.out.println("Avaliado com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarAvaliado() {
        System.out.print("Digite o ID do avaliado a atualizar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        Optional<Evaluated> existingEvaluated = evaluatedService.getEvaluatedById(id);
        if (existingEvaluated.isEmpty()) {
            System.out.println("Avaliado com ID " + id + " não encontrado para atualização.");
            return;
        }

        System.out.print("Digite o novo nome do avaliado (atual: " + existingEvaluated.get().getName() + "): ");
        String newName = scanner.nextLine();

        if (newName.trim().isEmpty()) {
            System.out.println("Erro: O novo nome do avaliado não pode ser vazio.");
            return;
        }

        Evaluated updatedDetails = Evaluated.builder().name(newName).build();
        try {
            Optional<Evaluated> updated = evaluatedService.updateEvaluated(id, updatedDetails);
            if (updated.isPresent()) {
                System.out.println("Avaliado atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar avaliado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar avaliado: " + e.getMessage());
        }
    }

    private static void excluirAvaliado() {
        System.out.print("Digite o ID do avaliado a excluir: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        try {
            boolean deleted = evaluatedService.deleteEvaluated(id);
            if (deleted) {
                System.out.println("Avaliado excluído com sucesso!");
            } else {
                System.out.println("Avaliado com ID " + id + " não encontrado para exclusão.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir avaliado: " + e.getMessage());
        }
    }

    private static void menuAvaliadores() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Avaliadores ---");
            System.out.println("1. Cadastrar Avaliador");
            System.out.println("2. Listar Todos os Avaliadores");
            System.out.println("3. Buscar Avaliador por ID");
            System.out.println("4. Atualizar Avaliador");
            System.out.println("5. Excluir Avaliador");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            subOpcao = lerOpcao();

            switch (subOpcao) {
                case 1:
                    cadastrarAvaliador();
                    break;
                case 2:
                    listarTodosAvaliadores();
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
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (subOpcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (subOpcao != 0);
    }

    private static void cadastrarAvaliador() {
        System.out.println("\n--- Cadastrar Novo Avaliador ---");
        System.out.print("Digite o nome do avaliador: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Erro: O nome do avaliador não pode ser vazio.");
            return;
        }

        Evaluator novoAvaliador = Evaluator.builder().name(name).build();
        try {
            Evaluator criado = evaluatorService.createEvaluator(novoAvaliador);
            System.out.println("Avaliador cadastrado com sucesso! ID: " + criado.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar avaliador: " + e.getMessage());
        }
    }

    private static void listarTodosAvaliadores() {
        System.out.println("\n--- Lista de Avaliadores ---");
        List<Evaluator> avaliadores = evaluatorService.getAllEvaluators();
        if (avaliadores.isEmpty()) {
            System.out.println("Nenhum avaliador cadastrado.");
            return;
        }
        avaliadores.forEach(e -> System.out.println("ID: " + e.getId() + " - Nome: " + e.getName()));
    }

    private static void buscarAvaliadorPorId() {
        System.out.print("Digite o ID do avaliador a buscar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        Optional<Evaluator> avaliador = evaluatorService.getEvaluatorById(id);
        if (avaliador.isPresent()) {
            System.out.println("Avaliador encontrado: ID: " + avaliador.get().getId() + " - Nome: " + avaliador.get().getName());
        } else {
            System.out.println("Avaliador com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarAvaliador() {
        System.out.print("Digite o ID do avaliador a atualizar: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        Optional<Evaluator> existingEvaluator = evaluatorService.getEvaluatorById(id);
        if (existingEvaluator.isEmpty()) {
            System.out.println("Avaliador com ID " + id + " não encontrado para atualização.");
            return;
        }

        System.out.print("Digite o novo nome do avaliador (atual: " + existingEvaluator.get().getName() + "): ");
        String newName = scanner.nextLine();

        if (newName.trim().isEmpty()) {
            System.out.println("Erro: O novo nome do avaliador não pode ser vazio.");
            return;
        }

        Evaluator updatedDetails = Evaluator.builder().name(newName).build();
        try {
            Optional<Evaluator> updated = evaluatorService.updateEvaluator(id, updatedDetails);
            if (updated.isPresent()) {
                System.out.println("Avaliador atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar avaliador.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar avaliador: " + e.getMessage());
        }
    }

    private static void excluirAvaliador() {
        System.out.print("Digite o ID do avaliador a excluir: ");
        Long id = null;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Erro: ID inválido. Digite um número inteiro.");
            return;
        } finally {
            scanner.nextLine();
        }

        try {
            boolean deleted = evaluatorService.deleteEvaluator(id);
            if (deleted) {
                System.out.println("Avaliador excluído com sucesso!");
            } else {
                System.out.println("Avaliador com ID " + id + " não encontrado para exclusão.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir avaliador: " + e.getMessage());
        }
    }
}