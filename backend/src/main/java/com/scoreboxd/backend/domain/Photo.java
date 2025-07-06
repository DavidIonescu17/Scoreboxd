package com.scoreboxd.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photos")
@Getter @Setter @NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    private Review review;
}
