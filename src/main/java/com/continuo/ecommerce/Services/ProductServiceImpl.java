package com.continuo.ecommerce.Services;


import com.continuo.ecommerce.Repository.ProductRepository;
import com.continuo.ecommerce.models.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServiceInterface {

    private final ProductRepository productRepository;

    @Override
    public Page<Products> filterProducts(String category, Double minPrice, Double maxPrice, Boolean available, Pageable pageable) {
        return productRepository.filterProducts(category,minPrice,maxPrice,available,pageable);
    }
}
