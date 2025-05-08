package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.services.CartService;
import com.example.OnlineStore.services.OrderService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/form")
    public String getOrderPage() {
        return "order-page";
    }

    @PostMapping("/confirm")
    public String confirmOrder(HttpServletRequest request, User user) {
        User completed_user = userService.getUserByUsername(request.getRemoteUser());
        List<Product> products = cartService.getCartProducts(completed_user);

        completed_user.setFirstName(user.getFirstName());
        completed_user.setLastName(user.getLastName());
        completed_user.setPhoneNumber(user.getPhoneNumber());
        completed_user.setAddress(user.getAddress());

        orderService.createOrder(completed_user, products);
        cartService.clearCart(completed_user);

        return "redirect:/";
    }
}
