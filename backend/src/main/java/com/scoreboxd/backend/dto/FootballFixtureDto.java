// src/main/java/com/scoreboxd/backend/external/dto/FootballFixtureDto.java
package com.scoreboxd.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record FootballFixtureDto(
        @JsonProperty("fixture") Fixture fixture,
        @JsonProperty("teams")   Teams   teams,
        @JsonProperty("score")   Score   score) {

    public record Fixture(long id, OffsetDateTime date) {}
    public record Teams(Team home, Team away) {
        public record Team(long id, String name, String logo) {}
    }
    public record Score(Fulltime fulltime) {
        public record Fulltime(Integer home, Integer away) {}
    }
}
