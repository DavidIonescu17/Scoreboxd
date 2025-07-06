package com.scoreboxd.backend.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scoreboxd.backend.domain.Tag;
public interface TagRepository extends JpaRepository<Tag, Integer> {}
    
