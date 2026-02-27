package dev.portfolio.dto;

import dev.portfolio.entity.Profile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {
    private Long id;
    private String name;
    private String title;
    private String bio;
    private String email;
    private String linkedinUrl;
    private String githubUrl;

    public static ProfileDto fromEntity(Profile p) {
        if (p == null) return null;
        return ProfileDto.builder()
                .id(p.getId())
                .name(p.getName())
                .title(p.getTitle())
                .bio(p.getBio())
                .email(p.getEmail())
                .linkedinUrl(p.getLinkedinUrl())
                .githubUrl(p.getGithubUrl())
                .build();
    }
}
