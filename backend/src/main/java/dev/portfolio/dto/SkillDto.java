package dev.portfolio.dto;

import dev.portfolio.entity.Skill;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SkillDto {
    private Long id;
    private String name;
    private String category;
    private int level;
    private int sortOrder;
    private List<String> keywords;

    public static SkillDto fromEntity(Skill s) {
        return SkillDto.builder()
                .id(s.getId())
                .name(s.getName())
                .category(s.getCategory())
                .level(s.getLevel())
                .sortOrder(s.getSortOrder())
                .keywords(s.getKeywords())
                .build();
    }
}
