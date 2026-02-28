package dev.portfolio.service;

import dev.portfolio.dto.ProfileDto;
import dev.portfolio.entity.Profile;
import dev.portfolio.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service métier pour la récupération du profil affiché sur l'accueil.
 * <p>
 * S'appuie sur {@link ProfileRepository} pour lire le premier profil (tri par sortOrder).
 * Un bloc try exécute l'accès repository et le mapping entité → DTO, un catch log et relance,
 * un finally trace la fin du traitement.
 * </p>
 *
 * @see ProfileRepository#findFirstByOrderBySortOrderAsc()
 * @see dev.portfolio.dto.ProfileDto
 */
@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;

    /**
     * Retourne le profil par défaut (premier par ordre d'affichage).
     *
     * @return Optional contenant le DTO ou vide si aucun profil en base
     */
    public Optional<ProfileDto> findDefault() {
        log.debug("ProfileService.findDefault() appelé");
        try {
            try {
                log.trace("Recherche du profil par ordre");
                Optional<Profile> opt = profileRepository.findFirstByOrderBySortOrderAsc();
                try {
                    if (opt.isEmpty()) {
                        log.debug("Aucun profil trouvé en base");
                        return Optional.empty();
                    }
                    try {
                        Profile entity = opt.get();
                        log.debug("Profil trouvé: id={}", entity.getId());
                        return Optional.of(ProfileDto.fromEntity(entity));
                    } finally {
                        log.trace("Mapping entité -> DTO terminé");
                    }
                } finally {
                    log.trace("Traitement optionnel terminé");
                }
            } finally {
                log.trace("Accès repository terminé");
            }
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du profil: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("ProfileService.findDefault() terminé");
        }
    }
}
