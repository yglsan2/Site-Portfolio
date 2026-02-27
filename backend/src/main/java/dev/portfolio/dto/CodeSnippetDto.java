package dev.portfolio.dto;

import dev.portfolio.entity.CodeSnippet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeSnippetDto {
    private Long id;
    private Long projectId;
    private String section;
    private String title;
    private String slug;
    private String language;
    private String description;
    private String code;
    private int sortOrder;

    public static CodeSnippetDto fromEntity(CodeSnippet s) {
        return CodeSnippetDto.builder()
                .id(s.getId())
                .projectId(s.getProject() != null ? s.getProject().getId() : null)
                .section(s.getSection())
                .title(s.getTitle())
                .slug(s.getSlug())
                .language(s.getLanguage())
                .description(s.getDescription())
                .code(s.getCode())
                .sortOrder(s.getSortOrder())
                .build();
    }
}
