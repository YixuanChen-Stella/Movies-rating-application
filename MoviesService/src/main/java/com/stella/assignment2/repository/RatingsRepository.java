package com.stella.assignment2.repository;

import com.stella.assignment2.entity.Rating;
import com.stella.assignment2.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingsRepository extends JpaRepository<Rating, RatingId> {
    List<Rating> findById_MovieId(int movieId);
}
