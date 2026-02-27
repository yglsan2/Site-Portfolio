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

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;

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
