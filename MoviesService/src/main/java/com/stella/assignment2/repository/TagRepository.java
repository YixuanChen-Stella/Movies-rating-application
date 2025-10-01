package com.stella.assignment2.repository;

import com.stella.assignment2.entity.Tag;
import com.stella.assignment2.entity.TagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, TagId> {
}
