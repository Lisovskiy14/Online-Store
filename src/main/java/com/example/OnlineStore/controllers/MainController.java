package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Category;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.services.CategoryService;
import com.example.OnlineStore.services.ProductService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class MainController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("categories", categoryService.getRootCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "main-page";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("image") MultipartFile image,
                                HttpServletRequest request,
                                Model model) {
        try {
            product.setImageUrl(productService.uploadImage(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.setSeller(userService.getUserByUsername(request.getRemoteUser()));
        productService.createProduct(product);

        return "redirect:/";
    }

    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.addCategory(category);

        return "redirect:/";
    }

    @GetMapping("/account")
    public String account() {
        return "account-page";
    }
}
