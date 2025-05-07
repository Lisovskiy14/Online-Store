package com.example.OnlineStore.repositories;

import com.example.OnlineStore.models.Cart;
import com.example.OnlineStore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getCartByOwner(User user);
}
