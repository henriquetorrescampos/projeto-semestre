package com.projeto_final.projeto_puc_go;

import com.projeto_final.projeto_puc_go.Console.ConsoleApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjetoPucGoApplication {

	public static void main(String[] args) {
		// Inicia a aplicação Spring Boot e obtém o contexto
		ConfigurableApplicationContext context = SpringApplication.run(ProjetoPucGoApplication.class, args);

		// Obtém a instância do bean ConsoleApplication do contexto Spring
		ConsoleApplication consoleApp = context.getBean(ConsoleApplication.class);

		// Chama o método para iniciar sua lógica de console
		consoleApp.runConsoleApplication();

		// Fecha o contexto da aplicação quando a lógica do console terminar
		context.close();
	}

}