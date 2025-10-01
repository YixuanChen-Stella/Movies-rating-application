package com.stella.assignment2.repository;

import com.stella.assignment2.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Integer> {
    Link findByMovieId(Integer movieId);
}
