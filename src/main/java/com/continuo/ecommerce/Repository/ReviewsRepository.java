package com.continuo.ecommerce.Repository;

import com.continuo.ecommerce.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,Long> {
    List<Reviews> findByProductId(long productId);
}
