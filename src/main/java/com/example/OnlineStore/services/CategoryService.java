package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Category;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.repositories.CategoryRepository;
import com.example.OnlineStore.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getRootCategories() {
        List<Category> categories = categoryRepository.findAllByParentIsNull();
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .collect(Collectors.toList());
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

    @Transactional
    public void deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new RuntimeException("Категорію з id '" + category_id + "' не знайдено"));

        List<Product> products = category.getProducts();
        for (Product product : products) {
            product.setCategory(null);
            productRepository.save(product);
        }

        category.getProducts().clear();
        categoryRepository.saveAndFlush(category);
        categoryRepository.delete(category);
    }
}
