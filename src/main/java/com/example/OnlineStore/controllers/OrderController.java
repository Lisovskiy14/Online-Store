package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.services.CartService;
import com.example.OnlineStore.services.OrderService;
import com.example.OnlineStore.services.UserService;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/confirm-data")
    public String getConfirmPage(HttpServletRequest request, Model model) {
        User user = userService.getUserByUsername(request.getRemoteUser());
        if (user.getFirstName() == null) {
            return "redirect:/order/form";
        }
        model.addAttribute("user", userService.getUserByUsername(
                request.getRemoteUser()));
        return "confirm-order-data-page";
    }

    @PostMapping("/create")
    public String createOrder(HttpServletRequest request) {
        User user = userService.getUserByUsername(request.getRemoteUser());
        List<Product> products = cartService.getCartProducts(user);

        orderService.createOrder(user, products);
        cartService.clearCart(user);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String getOrderPage() {
        return "new-order-data-page";
    }

    @PostMapping("/confirm-new-data")
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

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam("order_id") Long order_id, HttpServletRequest request) {
        orderService.deleteOrder(order_id);

        String referer = request.getHeader("referer");
        String path = ServletUriComponentsBuilder
                .fromUriString(referer)
                .build()
                .getPath();

        return "redirect:" + path;
    }
}
