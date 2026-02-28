package dev.portfolio.controller;

import dev.portfolio.dto.CodeSnippetDto;
import dev.portfolio.service.CodeSnippetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour les extraits de code (snippets) liés aux projets.
 * <p>
 * Une route : GET /api/snippets/project/{projectId}. Retourne les snippets
 * d'un projet donné, triés par ordre d'affichage. Utilisé par la page Codes
 * et le détail projet du frontend.
 * </p>
 *
 * @see CodeSnippetService
 * @see dev.portfolio.dto.CodeSnippetDto
 */
@RestController
@RequestMapping("/api/snippets")
@RequiredArgsConstructor
@CrossOrigin
public class CodeSnippetController {

    private static final Logger log = LoggerFactory.getLogger(CodeSnippetController.class);
    private final CodeSnippetService codeSnippetService;

    /**
     * Liste les extraits de code pour un projet donné.
     *
     * @param projectId identifiant du projet
     * @return 200 avec la liste des {@link CodeSnippetDto}
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<CodeSnippetDto>> listByProject(@PathVariable Long projectId) {
        log.debug("GET /api/snippets/project/{}", projectId);
        try {
            try {
                List<CodeSnippetDto> dtos = codeSnippetService.findByProjectId(projectId);
                try {
                    log.debug("{} snippet(s) renvoyé(s) pour projet {}", dtos.size(), projectId);
                    return ResponseEntity.ok(dtos);
                } finally {
                    log.trace("Réponse construite");
                }
            } finally {
                log.trace("Appel service terminé");
            }
        } catch (Exception e) {
            log.error("Erreur GET /api/snippets/project/{}: {}", projectId, e.getMessage(), e);
            throw e;
        } finally {
            log.trace("GET /api/snippets/project/{} terminé", projectId);
        }
    }
}
