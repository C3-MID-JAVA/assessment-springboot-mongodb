package org.example.financespro;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Main class for the FinancesPro application. */
@SpringBootApplication
public class FinancesProApplication {
  public static void main(String[] args) {
    // Load .env file
    Dotenv dotenv = Dotenv.configure().directory("./").load();

    // Set environment variables
    System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
    System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
    System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

    SpringApplication.run(FinancesProApplication.class, args);
  }
}
