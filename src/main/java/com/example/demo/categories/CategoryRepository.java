package com.example.demo.categories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findByIdParentWoocommerce(Integer idParentWoocommerce);
    Optional<Category> findByIdMoodle(Integer idMoodle);
}
