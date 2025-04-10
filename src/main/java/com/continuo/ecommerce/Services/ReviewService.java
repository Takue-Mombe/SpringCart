package com.continuo.ecommerce.Services;

import com.continuo.ecommerce.DTO.ReviewDTO;
import com.continuo.ecommerce.Repository.ProductRepository;
import com.continuo.ecommerce.Repository.ReviewsRepository;
import com.continuo.ecommerce.Repository.UserRepository;
import com.continuo.ecommerce.models.Products;
import com.continuo.ecommerce.models.Reviews;
import com.continuo.ecommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private final ReviewsRepository reviewsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public ReviewService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public Reviews addReview(Long productID,String customerEmail, ReviewDTO reviewDTO){

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Products products=productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Reviews reviews=new Reviews();


        reviews.setRating(reviewDTO.getRating());
        reviews.setComment(reviewDTO.getComment());
        reviews.setProduct(products);
        reviews.setCreatedAt(reviewDTO.getReviewDate());
        reviews.setCustomer(customer);


        return reviewsRepository.save(reviews);
    }

    public Reviews updateReview(Long productID,String customerEmail, ReviewDTO reviewDTO){

        Products products=productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Reviews reviews=new Reviews();

        reviews.setRating(reviewDTO.getRating());
        reviews.setComment(reviewDTO.getComment());
        reviews.setProduct(products);
        reviews.setCreatedAt(reviewDTO.getReviewDate());

        return reviewsRepository.save(reviews);

    }

    public void deleteReview(Long reviewID){
        reviewsRepository.deleteById(reviewID);
    }

    public List<Reviews> getAllReviews(){
        return reviewsRepository.findAll();
    }
}
