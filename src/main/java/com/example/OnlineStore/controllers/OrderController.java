package com.example.OnlineStore.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/form")
    public String getOrderPage() {
        return "order-page";
    }
}
