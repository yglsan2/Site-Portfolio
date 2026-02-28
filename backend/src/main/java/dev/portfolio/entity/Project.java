package dev.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant un projet du portfolio.
 * <p>
 * Table {@code projects} : titre, slug (URL), description, type (SOFTWARE/WEBSITE/OPEN_SOURCE),
 * technologies (liste), ordre, URLs projet/repo. Relation OneToMany vers {@link CodeSnippet}.
 * </p>
 *
 * @see CodeSnippet
 * @see dev.portfolio.repository.ProjectRepository
 * @see dev.portfolio.dto.ProjectDto
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    public enum ProjectType { SOFTWARE, WEBSITE, OPEN_SOURCE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectType type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology")
    @Builder.Default
    private List<String> technologies = new ArrayList<>();

    private int sortOrder;

    private String projectUrl;
    private String repoUrl;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CodeSnippet> snippets = new ArrayList<>();
}
