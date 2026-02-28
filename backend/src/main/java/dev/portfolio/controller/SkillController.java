package dev.portfolio.controller;

import dev.portfolio.dto.SkillDto;
import dev.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour les compétences (skills) du portfolio.
 * <p>
 * Une route : GET /api/skills. Retourne toutes les compétences triées par
 * catégorie et ordre. Utilisé par la page Compétences du frontend.
 * </p>
 *
 * @see SkillService
 * @see dev.portfolio.dto.SkillDto
 */
@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@CrossOrigin
public class SkillController {

    private static final Logger log = LoggerFactory.getLogger(SkillController.class);
    private final SkillService skillService;

    /**
     * Liste toutes les compétences (catégorie, nom, niveau, mots-clés).
     *
     * @return 200 avec la liste des {@link SkillDto}
     */
    @GetMapping
    public ResponseEntity<List<SkillDto>> list() {
        log.debug("GET /api/skills");
        try {
            try {
                List<SkillDto> dtos = skillService.findAll();
                try {
                    log.debug("{} compétence(s) renvoyée(s)", dtos.size());
                    return ResponseEntity.ok(dtos);
                } finally {
                    log.trace("Réponse construite");
                }
            } finally {
                log.trace("Appel service terminé");
            }
        } catch (Exception e) {
            log.error("Erreur GET /api/skills: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("GET /api/skills terminé");
        }
    }
}
