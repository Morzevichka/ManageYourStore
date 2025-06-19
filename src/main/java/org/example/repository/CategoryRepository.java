package org.example.repository;

import org.example.entity.Category;
import org.example.repository.impl.GenericRepository;
import org.example.annotation.Query;

import java.util.Optional;

public class CategoryRepository extends GenericRepository<Category, Long> {

    public CategoryRepository() {
        super(Category.class);
    }

    @Query(value = "SELECT * FROM CATEGORIES WHERE NAME = ?1")
    public Optional<Category> findByName(String name) {
        return getSingleResultQuery(name);
    }
}
