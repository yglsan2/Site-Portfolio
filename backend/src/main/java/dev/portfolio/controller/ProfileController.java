package dev.portfolio.controller;

import dev.portfolio.dto.ProfileDto;
import dev.portfolio.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST exposant le profil (nom, titre, bio, lien GitHub).
 * <p>
 * Une seule route : GET /api/profile. Retourne le premier profil configuré
 * (tri par sortOrder). Utilisé par la page d'accueil du frontend.
 * </p>
 *
 * @see ProfileService#findDefault()
 * @see dev.portfolio.dto.ProfileDto
 */
@RestController
@RequestMapping("/api/profile")
@CrossOrigin
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;

    /**
     * Récupère le profil par défaut (affiché sur l'accueil).
     * <p>
     * Un bloc try exécute l'appel au service et la construction de la réponse,
     * un seul catch log l'erreur et la relance, un finally trace la fin du traitement.
     * </p>
     *
     * @return 200 avec le {@link ProfileDto} si un profil existe, 404 sinon
     */
    @GetMapping
    public ResponseEntity<ProfileDto> get() {
        log.debug("GET /api/profile");
        try {
            try {
                var opt = profileService.findDefault();
                try {
                    if (opt.isEmpty()) {
                        log.warn("Aucun profil en base");
                        return ResponseEntity.notFound().build();
                    }
                    try {
                        log.debug("Profil renvoyé avec succès");
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
            log.error("Erreur GET /api/profile: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("GET /api/profile terminé");
        }
    }
}
