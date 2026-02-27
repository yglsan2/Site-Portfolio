package dev.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "code_snippets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String section;
    private String title;
    private String slug;
    private String language;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String code;

    private int sortOrder;
}
