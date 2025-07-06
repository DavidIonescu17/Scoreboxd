package com.scoreboxd.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@Getter @Setter @NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
