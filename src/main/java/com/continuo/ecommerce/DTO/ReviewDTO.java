package com.continuo.ecommerce.DTO;



import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;
}
