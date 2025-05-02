package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.User;
import com.example.OnlineStore.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ProductService {
    private final String ABSOLUTE_IMAGE_PATH = "C:\\Users\\nazar\\OneDrive\\Рабочий стол\\Education\\SI coursework\\OnlineStore\\src\\main\\resources\\static\\products-images\\";
    private final String RELATIVE_IMAGE_PATH = "/products-images/";
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        //product.setSeller(user.getUsername());
        productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
}
