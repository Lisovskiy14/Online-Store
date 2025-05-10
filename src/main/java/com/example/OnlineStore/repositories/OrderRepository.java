package com.example.OnlineStore.repositories;

import com.example.OnlineStore.models.Order;
import com.example.OnlineStore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByCustomer(User customer);
}
