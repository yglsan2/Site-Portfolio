package dev.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité JPA représentant le profil affiché sur l'accueil du portfolio.
 * <p>
 * Table {@code profile} : nom, titre, bio, email, liens LinkedIn/GitHub, ordre d'affichage.
 * Un seul profil est en général utilisé (celui avec le plus petit sortOrder).
 * </p>
 *
 * @see dev.portfolio.repository.ProfileRepository
 * @see dev.portfolio.dto.ProfileDto
 */
@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String title;
    @Column(columnDefinition = "CLOB")
    private String bio;
    private String email;
    private String linkedinUrl;
    private String githubUrl;
    private int sortOrder;
}
