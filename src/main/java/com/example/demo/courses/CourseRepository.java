package com.example.demo.courses;

import com.example.demo.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Category> findByName(String name);
}
