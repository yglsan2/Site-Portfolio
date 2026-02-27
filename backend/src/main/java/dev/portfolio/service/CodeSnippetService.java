package dev.portfolio.service;

import dev.portfolio.dto.CodeSnippetDto;
import dev.portfolio.repository.CodeSnippetRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeSnippetService {

    private static final Logger log = LoggerFactory.getLogger(CodeSnippetService.class);
    private final CodeSnippetRepository codeSnippetRepository;

    public List<CodeSnippetDto> findByProjectId(Long projectId) {
        log.debug("CodeSnippetService.findByProjectId(projectId={})", projectId);
        try {
            try {
                log.trace("Recherche snippets par projectId");
                var entities = codeSnippetRepository.findByProjectIdOrderBySortOrderAsc(projectId);
                try {
                    List<CodeSnippetDto> dtos = new ArrayList<>(entities.size());
                    try {
                        entities.forEach(s -> dtos.add(CodeSnippetDto.fromEntity(s)));
                        log.debug("{} snippet(s) trouvé(s) pour projet {}", dtos.size(), projectId);
                        return dtos;
                    } finally {
                        log.trace("Mapping terminé");
                    }
                } finally {
                    log.trace("Liste entités lue");
                }
            } finally {
                log.trace("Accès repository terminé");
            }
        } catch (Exception e) {
            log.error("Erreur CodeSnippetService.findByProjectId: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("CodeSnippetService.findByProjectId() terminé");
        }
    }
}
