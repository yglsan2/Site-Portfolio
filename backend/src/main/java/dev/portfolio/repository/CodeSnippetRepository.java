package dev.portfolio.repository;

import dev.portfolio.entity.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository Spring Data JPA pour {@link dev.portfolio.entity.CodeSnippet}.
 * Requête dérivée pour les snippets d'un projet, triés par ordre d'affichage.
 */
public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, Long> {

    /** Snippets du projet donné, triés par sortOrder. */
    List<CodeSnippet> findByProjectIdOrderBySortOrderAsc(Long projectId);
}
