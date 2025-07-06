package com.scoreboxd.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---- relationships ---- */

    @ManyToOne @JoinColumn(nullable = false)
    private User user;

    @ManyToOne @JoinColumn(nullable = false)
    private Match match;

    /* ---- data ---- */

    /** 0.5 – 5.0 step 0.5 */
    private BigDecimal rating;

    @Column(columnDefinition = "text")
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private Set<Photo> photos;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private Set<ReviewTag> tags;
}
