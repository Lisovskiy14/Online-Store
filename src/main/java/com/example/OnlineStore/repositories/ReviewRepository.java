package com.example.OnlineStore.repositories;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct(Product product);
}
