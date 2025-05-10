package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.services.ProductService;
import com.example.OnlineStore.services.ReviewService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/create")
    public String createReview(HttpServletRequest request,
                               @RequestParam(name = "product_id") Long product_id,
                               @RequestParam("review_text") String review_text) {

        User user = userService.getUserByUsername(request.getRemoteUser());
        Product product = productService.getProductById(product_id);

        try {
            reviewService.createReview(product, review_text, user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/product/" + product_id;
        }

        return "redirect:/product/" + product_id;
    }
}
