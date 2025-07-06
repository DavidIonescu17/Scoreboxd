package com.scoreboxd.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_tags")
@Getter @Setter @NoArgsConstructor
public class ReviewTag {

    @EmbeddedId
    private ReviewTagId id;

    @ManyToOne @MapsId("reviewId")
    private Review review;

    @ManyToOne @MapsId("tagId")
    private Tag tag;
}
