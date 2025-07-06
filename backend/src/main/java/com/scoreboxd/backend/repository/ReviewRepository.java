package com.scoreboxd.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.scoreboxd.backend.domain.Review;
public interface ReviewRepository extends JpaRepository<Review, Long> {}