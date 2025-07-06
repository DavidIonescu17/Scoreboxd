package com.scoreboxd.backend.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ReviewTagId implements Serializable {
    private Long reviewId;
    private Integer tagId;
}
