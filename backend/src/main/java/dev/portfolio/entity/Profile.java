package dev.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

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
