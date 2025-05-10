package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.Review;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.repositories.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void createReview(Product product, String text, User author) {
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Відгук не може бути порожнім");
        }

        Review review = new Review();
        review.setProduct(product);
        review.setText(text);
        review.setAuthor(author);
        reviewRepository.save(review);
        product.getReviews().add(review);
    }

    public void deleteReview(Long review_id) {
        reviewRepository.deleteById(review_id);
    }
}
