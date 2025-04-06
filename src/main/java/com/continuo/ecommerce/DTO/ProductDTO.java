package com.continuo.ecommerce.DTO;


import lombok.Data;

@Data
public class ProductDTO {

    private String name;
    private String description;
    private int price;
    private int quantity;
    private String imageUrl;
    private String category;

}
