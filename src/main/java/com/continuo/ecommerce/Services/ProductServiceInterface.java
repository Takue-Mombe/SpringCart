package com.continuo.ecommerce.Services;

import com.continuo.ecommerce.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceInterface {

    Page<Products> filterProducts(String category, Double minPrice, Double maxPrice, Boolean available, Pageable pageable);
}
 