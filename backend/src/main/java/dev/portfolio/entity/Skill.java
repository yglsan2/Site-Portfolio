package dev.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant une compétence (skill) du portfolio.
 * <p>
 * Table {@code skills} : nom, catégorie, niveau (%), ordre, liste de mots-clés.
 * </p>
 *
 * @see dev.portfolio.repository.SkillRepository
 * @see dev.portfolio.dto.SkillDto
 */
@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;
    private int level;
    private int sortOrder;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "skill_keywords", joinColumns = @JoinColumn(name = "skill_id"))
    @Column(name = "keyword")
    @Builder.Default
    private List<String> keywords = new ArrayList<>();
}
