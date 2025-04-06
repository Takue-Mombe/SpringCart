package com.continuo.ecommerce.Services;


import com.continuo.ecommerce.DTO.ProductDTO;
import com.continuo.ecommerce.Repository.ProductRepository;
import com.continuo.ecommerce.Repository.UserRepository;
import com.continuo.ecommerce.models.Products;
import com.continuo.ecommerce.models.User;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;


public interface ProductService {

    Products createProduct(String vendorEmail, ProductDTO productDTO);
    Products updateProduct(Long productId, String vendorEmail, ProductDTO productDTO);
    void deleteProduct(Long productId);
    List<Products> getAllProducts();
    Products getProductById(Long productId);
    Page<Products> filterProducts(String category, Double minPrice, Double maxPrice, Boolean available, org.springframework.data.domain.Pageable pageable);


}
