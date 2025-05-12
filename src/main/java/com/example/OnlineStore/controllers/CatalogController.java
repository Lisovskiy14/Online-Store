package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Category;
import com.example.OnlineStore.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalog")
@AllArgsConstructor
public class CatalogController {
    private final CategoryService categoryService;

    @GetMapping("/category/{slug}")
    public String getCategoryPage(@PathVariable String slug, Model model) {
        Category category = categoryService.getCategoryBySlug(slug);
        model.addAttribute("categories", categoryService.getRootCategories());
        model.addAttribute("products", category.getProducts());

        return "main-page";
    }
}
