package dev.portfolio.repository;

import dev.portfolio.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository Spring Data JPA pour l'entité {@link dev.portfolio.entity.Profile}.
 * <p>
 * Expose une requête dérivée pour récupérer le premier profil par ordre d'affichage.
 * </p>
 *
 * @see dev.portfolio.entity.Profile
 * @see dev.portfolio.service.ProfileService
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Retourne le premier profil trié par sortOrder ascendant (profil par défaut).
     *
     * @return Optional avec le profil ou vide si la table est vide
     */
    Optional<Profile> findFirstByOrderBySortOrderAsc();
}
