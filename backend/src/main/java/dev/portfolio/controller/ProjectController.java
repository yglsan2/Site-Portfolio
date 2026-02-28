package dev.portfolio.controller;

import dev.portfolio.dto.ProjectDto;
import dev.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour les projets du portfolio.
 * <p>
 * Routes : liste (GET /api/projects), par id (GET /api/projects/{id}),
 * par slug (GET /api/projects/slug/{slug}). Le frontend utilise le slug pour
 * les URLs lisibles (détail d'un projet).
 * </p>
 *
 * @see ProjectService
 * @see dev.portfolio.dto.ProjectDto
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

    /**
     * Liste tous les projets.
     *
     * @return 200 avec la liste des {@link ProjectDto}
     */
    @GetMapping
    public ResponseEntity<List<ProjectDto>> list() {
        log.debug("GET /api/projects");
        try {
            try {
                List<ProjectDto> dtos = projectService.findAll();
                try {
                    log.debug("{} projet(s) renvoyé(s)", dtos.size());
                    return ResponseEntity.ok(dtos);
                } finally {
                    log.trace("Réponse construite");
                }
            } finally {
                log.trace("Appel service terminé");
            }
        } catch (Exception e) {
            log.error("Erreur GET /api/projects: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("GET /api/projects terminé");
        }
    }

    /**
     * Récupère un projet par son identifiant technique.
     *
     * @param id identifiant du projet
     * @return 200 avec le projet, 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long id) {
        log.debug("GET /api/projects/{}", id);
        try {
            try {
                var opt = projectService.findById(id);
                try {
                    if (opt.isEmpty()) {
                        log.debug("Projet {} non trouvé", id);
                        return ResponseEntity.notFound().build();
                    }
                    try {
                        return ResponseEntity.ok(opt.get());
                    } finally {
                        log.trace("Réponse construite");
                    }
                } finally {
                    log.trace("Traitement optionnel terminé");
                }
            } finally {
                log.trace("Appel service terminé");
            }
        } catch (Exception e) {
            log.error("Erreur GET /api/projects/{}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            log.trace("GET /api/projects/{} terminé", id);
        }
    }

    /**
     * Récupère un projet par son slug (URL lisible, ex. "mon-projet-api").
     *
     * @param slug identifiant URL du projet
     * @return 200 avec le projet, 404 si non trouvé
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProjectDto> getBySlug(@PathVariable String slug) {
        log.debug("GET /api/projects/slug/{}", slug);
        try {
            try {
                var opt = projectService.findBySlug(slug);
                try {
                    if (opt.isEmpty()) {
                        log.debug("Projet slug={} non trouvé", slug);
                        return ResponseEntity.notFound().build();
                    }
                    try {
                        return ResponseEntity.ok(opt.get());
                    } finally {
                        log.trace("Réponse construite");
                    }
                } finally {
                    log.trace("Traitement optionnel terminé");
                }
            } finally {
                log.trace("Appel service terminé");
            }
        } catch (Exception e) {
            log.error("Erreur GET /api/projects/slug/{}: {}", slug, e.getMessage(), e);
            throw e;
        } finally {
            log.trace("GET /api/projects/slug/{} terminé", slug);
        }
    }
}
