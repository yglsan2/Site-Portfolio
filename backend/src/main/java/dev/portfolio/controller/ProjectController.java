package dev.portfolio.controller;

import dev.portfolio.dto.ProjectDto;
import dev.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

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
