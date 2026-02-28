package dev.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot du portfolio.
 * <p>
 * Lance le contexte Spring et démarre le serveur embarqué (Tomcat).
 * Les contrôleurs exposent l'API REST (profil, projets, compétences, snippets)
 * consommée par le frontend Vue.js.
 * </p>
 *
 * @see dev.portfolio.controller.ProfileController
 * @see dev.portfolio.controller.ProjectController
 * @see dev.portfolio.controller.SkillController
 * @see dev.portfolio.controller.CodeSnippetController
 */
@SpringBootApplication
public class PortfolioApplication {

    /**
     * Démarre l'application.
     *
     * @param args arguments passés à la JVM (optionnel)
     */
    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }
}
