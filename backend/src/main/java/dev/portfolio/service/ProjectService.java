package dev.portfolio.service;

import dev.portfolio.dto.ProjectDto;
import dev.portfolio.entity.Project;
import dev.portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les projets du portfolio.
 * <p>
 * Expose la liste des projets, la recherche par id et par slug.
 * Chaque méthode encapsule l'accès repository et le mapping entité → DTO
 * dans un try/catch/finally avec logs.
 * </p>
 *
 * @see ProjectRepository
 * @see dev.portfolio.dto.ProjectDto
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository projectRepository;

    /**
     * Retourne tous les projets, mappés en DTOs.
     *
     * @return liste des ProjectDto (jamais null)
     */
    public List<ProjectDto> findAll() {
        log.debug("ProjectService.findAll() appelé");
        try {
            try {
                log.trace("Accès repository findAll");
                List<Project> entities = projectRepository.findAll();
                try {
                    List<ProjectDto> dtos = new ArrayList<>(entities.size());
                    try {
                        for (Project p : entities) {
                            dtos.add(ProjectDto.fromEntity(p));
                        }
                        log.debug("{} projet(s) trouvé(s)", dtos.size());
                        return dtos;
                    } finally {
                        log.trace("Mapping entités -> DTOs terminé");
                    }
                } finally {
                    log.trace("Liste entités lue");
                }
            } finally {
                log.trace("Accès repository terminé");
            }
        } catch (Exception e) {
            log.error("Erreur ProjectService.findAll: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("ProjectService.findAll() terminé");
        }
    }

    /**
     * Retourne le projet dont le slug correspond.
     *
     * @param slug identifiant URL du projet
     * @return Optional avec le DTO ou vide
     */
    public Optional<ProjectDto> findBySlug(String slug) {
        log.debug("ProjectService.findBySlug(slug={})", slug);
        try {
            try {
                log.trace("Recherche par slug");
                Optional<Project> opt = projectRepository.findBySlug(slug);
                try {
                    Optional<ProjectDto> result = opt.map(ProjectDto::fromEntity);
                    try {
                        log.debug("Projet par slug trouvé: {}", result.isPresent());
                        return result;
                    } finally {
                        log.trace("Mapping terminé");
                    }
                } finally {
                    log.trace("Recherche terminée");
                }
            } finally {
                log.trace("Accès repository terminé");
            }
        } catch (Exception e) {
            log.error("Erreur ProjectService.findBySlug: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("ProjectService.findBySlug() terminé");
        }
    }

    /**
     * Retourne le projet par son identifiant technique.
     *
     * @param id identifiant du projet
     * @return Optional avec le DTO ou vide
     */
    public Optional<ProjectDto> findById(Long id) {
        log.debug("ProjectService.findById(id={})", id);
        try {
            try {
                log.trace("Recherche par id");
                Optional<Project> opt = projectRepository.findById(id);
                try {
                    Optional<ProjectDto> result = opt.map(ProjectDto::fromEntity);
                    try {
                        log.debug("Projet par id trouvé: {}", result.isPresent());
                        return result;
                    } finally {
                        log.trace("Mapping terminé");
                    }
                } finally {
                    log.trace("Recherche terminée");
                }
            } finally {
                log.trace("Accès repository terminé");
            }
        } catch (Exception e) {
            log.error("Erreur ProjectService.findById: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("ProjectService.findById() terminé");
        }
    }
}
