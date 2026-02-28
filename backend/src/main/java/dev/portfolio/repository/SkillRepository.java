package dev.portfolio.repository;

import dev.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository Spring Data JPA pour {@link dev.portfolio.entity.Skill}.
 * Requête dérivée pour récupérer toutes les compétences triées par catégorie et ordre.
 */
public interface SkillRepository extends JpaRepository<Skill, Long> {

    /** Toutes les compétences, tri catégorie puis ordre. */
    List<Skill> findAllByOrderByCategoryAscSortOrderAsc();
}
