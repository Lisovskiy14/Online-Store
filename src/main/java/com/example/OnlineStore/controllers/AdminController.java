package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Category;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.models.enums.Role;
import com.example.OnlineStore.services.CategoryService;
import com.example.OnlineStore.services.OrderService;
import com.example.OnlineStore.services.ReviewService;
import com.example.OnlineStore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    @GetMapping("/create-category/form")
    public String getCreateCategoryPage() {
        return "create-category-page";
    }

    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.addCategory(category);

        return "redirect:/";
    }

    @GetMapping("/show-users")
    public String getUsersManagePage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-manage-page";
    }

    @PostMapping("/add-role")
    public String addRole(@RequestParam("username") String username,
                          @RequestParam("role") Role role) {
        userService.addRoleToUser(username, role);
        return "redirect:/admin/show-users";
    }

    @PostMapping("/delete-role")
    public String deleteRole(@RequestParam("username") String username,
                             @RequestParam("role") Role role) {
        userService.deleteRoleFromUser(username, role);
        return "redirect:/admin/show-users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("username") String username) {
        userService.deleteUser(username);
        return "redirect:/admin/show-users";
    }

    @PostMapping("/delete-review")
    public String deleteReview(@RequestParam("review_id") Long review_id,
                               @RequestParam("product_id") Long product_id) {
        reviewService.deleteReview(review_id);
        return "redirect:/product/" + product_id;
    }

    @PostMapping("/confirm-order")
    public String finishOrder(@RequestParam("order_id") Long order_id, HttpServletRequest request) {
        orderService.confirmOrder(order_id);

        String referer = request.getHeader("referer");
        String path = ServletUriComponentsBuilder
                .fromUriString(referer)
                .build()
                .getPath();

        return "redirect:" + path;
    }

}
