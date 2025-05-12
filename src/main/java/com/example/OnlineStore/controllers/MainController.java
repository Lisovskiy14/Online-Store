package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.services.CategoryService;
import com.example.OnlineStore.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("categories", categoryService.getRootCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "main-page";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getRootCategories());
        return "main-page";
    }
}
