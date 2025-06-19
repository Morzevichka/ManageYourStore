package org.example.services;

import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.example.services.impl.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepository();

    @Override
    public Category findCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.update(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }
}
