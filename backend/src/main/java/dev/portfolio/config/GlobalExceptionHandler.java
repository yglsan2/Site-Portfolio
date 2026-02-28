package dev.portfolio.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Centralise la gestion des exceptions non gérées dans les contrôleurs.
 * <p>
 * Toute exception levée par un contrôleur est interceptée ici : on log l'erreur
 * et on renvoie une réponse JSON cohérente (500 avec message) au lieu d'une
 * page d'erreur brute. Évite de divulguer des stack traces au client.
 * </p>
 *
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Intercepte toute exception et renvoie une réponse 500 avec un corps JSON.
     *
     * @param e l'exception levée
     * @return ResponseEntity avec status 500 et body {"error": "...", "message": "..."}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        log.error("Erreur API: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Erreur serveur",
                        "message", e.getMessage() != null ? e.getMessage() : "Internal server error"
                ));
    }
}
