package com.continuo.ecommerce.Controller;


import com.continuo.ecommerce.DTO.ProductDTO;
import com.continuo.ecommerce.Services.ProductService;
import com.continuo.ecommerce.models.Products;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/vendor/{email}")
    public ResponseEntity<Products> addProduct(@PathVariable("email") String email,
                                               @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(email, productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id,
                                                  @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Products> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
