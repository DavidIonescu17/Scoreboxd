package com.scoreboxd.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "follows")
@Getter @Setter @NoArgsConstructor
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne @MapsId("followerId")
    private User follower;

    @ManyToOne @MapsId("followeeId")
    private User followee;

    private OffsetDateTime since = OffsetDateTime.now();
}