package dev.portfolio.service;

import dev.portfolio.dto.ProfileDto;
import dev.portfolio.entity.Profile;
import dev.portfolio.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void findDefault_whenProfileExists_returnsDto() {
        Profile entity = Profile.builder()
                .id(1L)
                .name("Jane Dev")
                .title("Développeuse full-stack")
                .bio("Bio courte")
                .email("jane@example.com")
                .linkedinUrl("https://linkedin.com/in/jane")
                .githubUrl("https://github.com/jane")
                .sortOrder(0)
                .build();
        given(profileRepository.findFirstByOrderBySortOrderAsc()).willReturn(Optional.of(entity));

        Optional<ProfileDto> result = profileService.findDefault();

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("Jane Dev");
        assertThat(result.get().getGithubUrl()).isEqualTo("https://github.com/jane");
        verify(profileRepository).findFirstByOrderBySortOrderAsc();
    }

    @Test
    void findDefault_whenNoProfile_returnsEmpty() {
        given(profileRepository.findFirstByOrderBySortOrderAsc()).willReturn(Optional.empty());

        Optional<ProfileDto> result = profileService.findDefault();

        assertThat(result).isEmpty();
        verify(profileRepository).findFirstByOrderBySortOrderAsc();
    }

    @Test
    void findDefault_whenRepositoryThrows_rethrows() {
        given(profileRepository.findFirstByOrderBySortOrderAsc()).willThrow(new RuntimeException("DB error"));
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> profileService.findDefault());
    }
}
