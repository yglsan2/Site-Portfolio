package dev.portfolio.repository;

import dev.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository Spring Data JPA pour {@link dev.portfolio.entity.Project}.
 * Requête dérivée findBySlug pour la résolution par URL.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /** Retourne le projet dont le slug correspond. */
    Optional<Project> findBySlug(String slug);
}
