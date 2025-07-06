package com.scoreboxd.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FollowId implements Serializable {
    private UUID followerId;
    private UUID followeeId;
}
