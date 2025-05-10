package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Cart;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void createCart(User user) {
        Cart cart = new Cart();
        cart.setOwner(user);
        cartRepository.save(cart);
    }

    public void addProduct(User customer, Product product) {
        Cart cart = cartRepository.getCartByOwner(customer);
        if (cart == null) {
            createCart(customer);
            cart = cartRepository.getCartByOwner(customer);
        }
        if (cart.getProducts().contains(product)) {
            throw new RuntimeException("Даний товар уже є в кошику");
        }
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public List<Product> getCartProducts(User user) {
        try {
            return cartRepository.getCartByOwner(user).getProducts();
        } catch (NullPointerException e) {
            createCart(user);
            return cartRepository.getCartByOwner(user).getProducts();
        }
    }

    public void deleteProduct(User user, Long product_id) {
        Cart cart = cartRepository.getCartByOwner(user);
        cart.getProducts().removeIf(product -> product.getId().equals(product_id));
        cartRepository.save(cart);
    }

    public Double getTotalPrice(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clearCart(User user) {
        Cart cart = cartRepository.getCartByOwner(user);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }
}
