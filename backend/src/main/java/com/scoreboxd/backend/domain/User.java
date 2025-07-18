package com.scoreboxd.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")     // “user” is reserved in many DBs
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String email;

    private Boolean prefersFootball = false;
    private Boolean prefersTennis  = false;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    /* ---- relationships ---- */

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private Set<Follow> following;

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL)
    private Set<Follow> followers;
}
