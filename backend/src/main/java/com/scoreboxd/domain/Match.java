package com.scoreboxd.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "matches")
@Getter @Setter @NoArgsConstructor
public class Match {

    @Id                         // use fixture / match ID from external API
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    private String matchType;   // final, group-stage, friendly …
    private LocalDate matchDate;
    private String competition;
    private String score;       // nullable until game finished

    /* ---- relationships ---- */

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    @OneToMany(mappedBy = "match")
    private Set<Review> reviews;
}
