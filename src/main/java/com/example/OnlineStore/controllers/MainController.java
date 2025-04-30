package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "main-page";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("image") MultipartFile image,
                                Model model) {
        try {
            product.setImageUrl(productService.uploadImage(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        productService.createProduct(product);

        return "redirect:/";
    }
}
