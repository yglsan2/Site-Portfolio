package dev.portfolio.service;

import dev.portfolio.dto.CodeSnippetDto;
import dev.portfolio.entity.CodeSnippet;
import dev.portfolio.entity.Project;
import dev.portfolio.repository.CodeSnippetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CodeSnippetServiceTest {

    @Mock
    private CodeSnippetRepository codeSnippetRepository;

    @InjectMocks
    private CodeSnippetService codeSnippetService;

    @Test
    void findByProjectId_returnsDtosOrdered() {
        Project project = Project.builder().id(1L).title("Projet").slug("projet").build();
        CodeSnippet s1 = CodeSnippet.builder().id(10L).project(project).section("Backend").title("Snippet 1").slug("s1").language("java").sortOrder(1).build();
        CodeSnippet s2 = CodeSnippet.builder().id(11L).project(project).section("Backend").title("Snippet 2").slug("s2").language("java").sortOrder(2).build();
        given(codeSnippetRepository.findByProjectIdOrderBySortOrderAsc(1L)).willReturn(List.of(s1, s2));

        List<CodeSnippetDto> result = codeSnippetService.findByProjectId(1L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(11L);
        verify(codeSnippetRepository).findByProjectIdOrderBySortOrderAsc(1L);
    }

    @Test
    void findByProjectId_whenEmpty_returnsEmptyList() {
        given(codeSnippetRepository.findByProjectIdOrderBySortOrderAsc(99L)).willReturn(List.of());

        List<CodeSnippetDto> result = codeSnippetService.findByProjectId(99L);

        assertThat(result).isEmpty();
        verify(codeSnippetRepository).findByProjectIdOrderBySortOrderAsc(99L);
    }
}
