package com.scoreboxd.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReviewTagId implements Serializable {
    private Long reviewId;
    private Integer tagId;
}
