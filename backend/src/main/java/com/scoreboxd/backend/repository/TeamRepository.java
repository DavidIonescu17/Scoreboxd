package com.scoreboxd.backend.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scoreboxd.backend.domain.Team;
public interface TeamRepository extends JpaRepository<Team, Integer> {}
    
