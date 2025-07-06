package com.scoreboxd.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.scoreboxd.backend.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {}