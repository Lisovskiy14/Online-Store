package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Category;
import com.example.OnlineStore.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getRootCategories() {
        return categoryRepository.findAllByParentIsNull();
    }

    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug).orElseThrow(() ->
                new NoSuchElementException("Категорію не знайдено"));
    }

    public Category getCategoryWithProducts(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Категорію з slug '" + slug + "' не знайдено"));
    }

    public void addSubcategory(String parentSlug, Category newCategory) {
        categoryRepository.findBySlug(parentSlug).ifPresent(parent -> {
            newCategory.setParent(parent);
            categoryRepository.save(newCategory);
        });
    }

    public void addCategory(Category newCategory) {
        categoryRepository.save(newCategory);
    }
}
