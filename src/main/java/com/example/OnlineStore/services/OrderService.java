package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Order;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void createOrder(User customer, List<Product> products) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(new ArrayList<>(products));
        order.setPrice(products.stream().mapToDouble(Product::getPrice).sum());
        order.setStatus("Створено");
        orderRepository.save(order);
    }

    public void deleteOrder(Long order_id) {
        orderRepository.deleteById(order_id);
    }
}
