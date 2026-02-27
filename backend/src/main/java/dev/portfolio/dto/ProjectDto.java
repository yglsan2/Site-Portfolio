package dev.portfolio.dto;

import dev.portfolio.entity.Project;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String type;
    private List<String> technologies;
    private int sortOrder;
    private String projectUrl;
    private String repoUrl;

    public static ProjectDto fromEntity(Project p) {
        return ProjectDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .slug(p.getSlug())
                .description(p.getDescription())
                .type(p.getType() != null ? p.getType().name() : null)
                .technologies(p.getTechnologies())
                .sortOrder(p.getSortOrder())
                .projectUrl(p.getProjectUrl())
                .repoUrl(p.getRepoUrl())
                .build();
    }
}
