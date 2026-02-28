package dev.portfolio.service;

import dev.portfolio.dto.ProjectDto;
import dev.portfolio.entity.Project;
import dev.portfolio.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void findAll_returnsMappedDtos() {
        Project p1 = Project.builder().id(1L).title("Projet A").slug("projet-a").sortOrder(0).build();
        Project p2 = Project.builder().id(2L).title("Projet B").slug("projet-b").sortOrder(1).build();
        given(projectRepository.findAll()).willReturn(List.of(p1, p2));
        List<ProjectDto> result = projectService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getSlug()).isEqualTo("projet-a");
        assertThat(result.get(1).getId()).isEqualTo(2L);
        verify(projectRepository).findAll();
    }

    @Test
    void findAll_whenEmpty_returnsEmptyList() {
        given(projectRepository.findAll()).willReturn(List.of());
        assertThat(projectService.findAll()).isEmpty();
        verify(projectRepository).findAll();
    }

    @Test
    void findById_whenExists_returnsDto() {
        Project entity = Project.builder().id(10L).title("Mon API").slug("mon-api").sortOrder(0).build();
        given(projectRepository.findById(10L)).willReturn(Optional.of(entity));
        Optional<ProjectDto> result = projectService.findById(10L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(10L);
        assertThat(result.get().getSlug()).isEqualTo("mon-api");
        verify(projectRepository).findById(10L);
    }

    @Test
    void findById_whenNotExists_returnsEmpty() {
        given(projectRepository.findById(999L)).willReturn(Optional.empty());
        assertThat(projectService.findById(999L)).isEmpty();
        verify(projectRepository).findById(999L);
    }

    @Test
    void findBySlug_whenExists_returnsDto() {
        Project entity = Project.builder().id(5L).title("BarrelMCD").slug("barrelmcd-python").sortOrder(0).build();
        given(projectRepository.findBySlug("barrelmcd-python")).willReturn(Optional.of(entity));
        Optional<ProjectDto> result = projectService.findBySlug("barrelmcd-python");
        assertThat(result).isPresent();
        assertThat(result.get().getSlug()).isEqualTo("barrelmcd-python");
        verify(projectRepository).findBySlug("barrelmcd-python");
    }

    @Test
    void findBySlug_whenNotExists_returnsEmpty() {
        given(projectRepository.findBySlug("inconnu")).willReturn(Optional.empty());
        assertThat(projectService.findBySlug("inconnu")).isEmpty();
        verify(projectRepository).findBySlug("inconnu");
    }
}
