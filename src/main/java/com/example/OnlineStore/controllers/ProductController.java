package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.models.enums.Role;
import com.example.OnlineStore.repositories.UserRepository;
import com.example.OnlineStore.services.CategoryService;
import com.example.OnlineStore.services.ProductService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public String getProductPage(HttpServletRequest request, @PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-page";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("product_id") Long product_id) {
        productService.deleteProduct(product_id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        model.addAttribute("categories", categoryService.getRootCategories());
        return "create-product-page";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("image") MultipartFile image,
                                HttpServletRequest request) {
        try {
            product.setImageUrl(productService.uploadImage(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.setSeller(userService.getUserByUsername(request.getRemoteUser()));
        productService.createProduct(product);

        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editProductPage(@RequestParam("product_id") Long product_id, Model model) {
        model.addAttribute("categories", categoryService.getRootCategories());
        model.addAttribute("product_id", product_id);
        return "edit-product-page";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute Product product,
                              @RequestParam(value = "image", required = false) MultipartFile image,
                              @RequestParam(value = "product_id") Long product_id) {

        if (image != null && !image.isEmpty()) {
            try {
                product.setImageUrl(productService.uploadImage(image));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Product editedProduct = productService.getProductById(product_id);
        productService.updateProduct(editedProduct, product);

        return "redirect:/account";
    }
}
