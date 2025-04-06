package com.continuo.ecommerce.Services;


import com.continuo.ecommerce.DTO.ProductDTO;
import com.continuo.ecommerce.Repository.ProductRepository;
import com.continuo.ecommerce.Repository.UserRepository;
import com.continuo.ecommerce.models.Products;
import com.continuo.ecommerce.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Products createProduct(String vendorEmail, ProductDTO productDTO) {

        User vendor =userRepository.findByEmail(vendorEmail)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Products products = new Products();
        products.setName(productDTO.getName());
        products.setDescription(productDTO.getDescription());
        products.setPrice(productDTO.getPrice());
        products.setQuantity(productDTO.getQuantity());
        products.setImageUrl(productDTO.getImageUrl());
        products.setCategory(productDTO.getCategory());
        products.setVendor(vendor);
        products.setAvailable(true);
        products.setCreatedAt(LocalDateTime.now());
        products.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(products);

    }

    public Products updateProduct(Long productId, ProductDTO productDTO) {

        Products products =productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        products.setName(productDTO.getName());
        products.setDescription(productDTO.getDescription());
        products.setPrice(productDTO.getPrice());
        products.setQuantity(productDTO.getQuantity());
        products.setImageUrl(productDTO.getImageUrl());
        products.setCategory(productDTO.getCategory());
        products.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(products);
    }
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
    public Products getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

    }
}
