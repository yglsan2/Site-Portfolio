package dev.portfolio.service;

import dev.portfolio.dto.SkillDto;
import dev.portfolio.entity.Skill;
import dev.portfolio.repository.SkillRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests unitaires de {@link SkillService}.
 */
@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    @Test
    @DisplayName("findAll retourne les compétences mappées en DTO")
    void findAll_returnsMappedDtos() {
        Skill s1 = Skill.builder().id(1L).name("Java").category("Backend").level(90).sortOrder(0).keywords(List.of("Spring")).build();
        Skill s2 = Skill.builder().id(2L).name("Vue.js").category("Frontend").level(85).sortOrder(0).keywords(List.of("Vue3")).build();
        given(skillRepository.findAllByOrderByCategoryAscSortOrderAsc()).willReturn(List.of(s1, s2));

        List<SkillDto> result = skillService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Java");
        assertThat(result.get(0).getCategory()).isEqualTo("Backend");
        assertThat(result.get(1).getName()).isEqualTo("Vue.js");
        verify(skillRepository).findAllByOrderByCategoryAscSortOrderAsc();
    }

    @Test
    @DisplayName("findAll retourne une liste vide quand aucune compétence")
    void findAll_whenEmpty_returnsEmptyList() {
        given(skillRepository.findAllByOrderByCategoryAscSortOrderAsc()).willReturn(List.of());

        List<SkillDto> result = skillService.findAll();

        assertThat(result).isEmpty();
        verify(skillRepository).findAllByOrderByCategoryAscSortOrderAsc();
    }
}
