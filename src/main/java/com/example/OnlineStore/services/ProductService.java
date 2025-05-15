package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductService {
    private final String ABSOLUTE_IMAGE_PATH = "C:\\Users\\nazar\\OneDrive\\Рабочий стол\\Education\\SI coursework\\OnlineStore\\src\\main\\resources\\static\\products-images\\";
    private final String RELATIVE_IMAGE_PATH = "/products-images/";
    private final ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Товар не знайдено"));
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsBySeller(User seller) {
        return productRepository.getProductsBySeller(seller);
    }

    public String uploadImage(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = ABSOLUTE_IMAGE_PATH + fileName;

                File destinationfile = new File(filePath);
                file.transferTo(destinationfile);

                return RELATIVE_IMAGE_PATH + fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new NullPointerException("Зображення не було завантажено користувачем!");
    }

    @Transactional
    public void sellProduct(Product product) {
        User seller = product.getSeller();
        product.setNumber(product.getNumber() - 1);
        seller.setMoneyEarned(seller.getMoneyEarned() + product.getPrice());

        if (product.getNumber() <= 0) {
            productRepository.delete(product);
        }

        try {
            Path relativePath = Paths.get(product.getImageUrl());
            String fileName = relativePath.getFileName().toString();
            Path path = Paths.get(ABSOLUTE_IMAGE_PATH + fileName);
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.searchProducts(keyword);
    }

    public void updateProduct(Product editedProduct, Product product) {
        if (product.getName() != null && !product.getName().trim().isEmpty()) {
            editedProduct.setName(product.getName().trim());
        }

        if (product.getDescription() != null && !product.getDescription().trim().isEmpty()) {
            editedProduct.setDescription(product.getDescription().trim());
        }

        if (product.getPrice() != null && product.getPrice() > 0) {
            editedProduct.setPrice(product.getPrice());
        }

        if (product.getCategory() != null) {
            editedProduct.setCategory(product.getCategory());
        }

        if (product.getImageUrl() != null && !product.getImageUrl().trim().isEmpty()) {
            editedProduct.setImageUrl(product.getImageUrl().trim());
        }

        productRepository.save(editedProduct);
    }
}
