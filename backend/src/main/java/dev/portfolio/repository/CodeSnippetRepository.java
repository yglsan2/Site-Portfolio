package dev.portfolio.repository;

import dev.portfolio.entity.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, Long> {
    List<CodeSnippet> findByProjectIdOrderBySortOrderAsc(Long projectId);
}
