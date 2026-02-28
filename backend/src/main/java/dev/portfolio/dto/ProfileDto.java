package dev.portfolio.dto;

import dev.portfolio.entity.Profile;
import lombok.Builder;
import lombok.Data;

/**
 * DTO exposé par l'API pour le profil (accueil).
 * <p>
 * Contient les champs publics : id, name, title, bio, email, linkedinUrl, githubUrl.
 * Méthode statique {@link #fromEntity(Profile)} pour mapper une entité vers ce DTO.
 * </p>
 *
 * @see Profile
 * @see dev.portfolio.controller.ProfileController
 */
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

    /**
     * Construit un DTO à partir de l'entité Profile.
     *
     * @param p entité (peut être null)
     * @return le DTO ou null si p est null
     */
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
