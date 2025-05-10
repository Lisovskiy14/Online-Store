package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.repositories.CategoryRepository;
import com.example.OnlineStore.repositories.ProductRepository;
import com.example.OnlineStore.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    }
}
