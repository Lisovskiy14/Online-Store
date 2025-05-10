package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Order;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.models.enums.Role;
import com.example.OnlineStore.services.OrderService;
import com.example.OnlineStore.services.ProductService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccountController {
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/account")
    public String getAccountPage(HttpServletRequest request, Model model) {
        User user = userService.getUserByUsername(request.getRemoteUser());
        model.addAttribute("user", user);
        model.addAttribute("wrongClient", false);
        model.addAttribute("myOrders", orderService.getCustomerOrders(user));

        String primaryRole = userService.getPrimaryRole(user);

        if (primaryRole.equals("ROLE_SELLER")) {
            model.addAttribute("products", productService.getProductsBySeller(user));
        } else if (primaryRole.equals("ROLE_ADMIN")) {
            model.addAttribute("allOrders", orderService.getAllOrders());
            model.addAttribute("products", productService.getProductsBySeller(user));
        }

        return "account-page";
    }

    @GetMapping("/account/{username}")
    public String getAccountPage(@PathVariable String username, Model model) {
        User seller = userService.getUserByUsername(username);
        if (!seller.getRoles().contains(Role.ROLE_SELLER) &&
                !seller.getRoles().contains(Role.ROLE_ADMIN)) {
            return "redirect:/";
        }
        model.addAttribute("user", seller);

        List<Product> products = productService.getProductsBySeller(seller);
        model.addAttribute("products", products);

        model.addAttribute("wrongClient", true);
        return "account-page";
    }
}
