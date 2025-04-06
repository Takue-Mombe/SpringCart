package com.continuo.ecommerce.Repository;

import com.continuo.ecommerce.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Long> {

    List<Products> findByCategory(String category);
    List<Products>findByVendor_Email(String email);

}
