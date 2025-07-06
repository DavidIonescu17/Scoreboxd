package com.scoreboxd.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Team {

    @Id
    private Integer id;                   // same ID as from API-Football / Tennis API

    private String name;
    private String logoUrl;

    @Enumerated(EnumType.STRING)
    private Sport sport;
}
