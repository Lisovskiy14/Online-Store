package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.services.CartService;
import com.example.OnlineStore.services.ProductService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/add")
    public String addToCart(HttpServletRequest request,
                            @RequestParam("product_id") Long product_id,
                            Model model) {

        try {
            cartService.addProduct(userService.getUserByUsername(request.getRemoteUser()),
                    productService.getProductById(product_id));
        } catch (RuntimeException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }


        String referer = request.getHeader("referer");
        String path = ServletUriComponentsBuilder
                .fromUriString(referer)
                .build()
                .getPath();

        return "redirect:" + path;
    }

    @GetMapping("/get")
    public String getCartPage(HttpServletRequest request, Model model) {
        List<Product> products = cartService.getCartProducts(
                userService.getUserByUsername(request.getRemoteUser()));
        model.addAttribute("products", products);
        model.addAttribute("total_price", cartService.getTotalPrice(products));

        return "cart-page";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(HttpServletRequest request,
                                @RequestParam("product_id") Long product_id) {

        cartService.deleteProduct(userService.getUserByUsername(request.getRemoteUser()),
                product_id);
        return "redirect:/cart/get";
    }
}
