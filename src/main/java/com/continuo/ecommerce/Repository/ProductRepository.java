package com.continuo.ecommerce.Repository;

import com.continuo.ecommerce.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Long> {

    List<Products> findByCategory(String category);
    List<Products>findByVendor_Email(String email);


    @Query("select p from  Products p where " +
    "(:category IS NULL OR p.category= :category) AND " +
            "(:minPrice IS Null OR p.price >= :minPrice) AND " +
            "(:maxPrice is null or p.price<= :maxPrice) and " +
            "(:available is null or p.available = :available)")
    Page<Products> filterProducts (@Param("category") String category,
                                   @Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   @Param("available") Boolean available,
                                   Pageable pageable);

}
