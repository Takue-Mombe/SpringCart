package com.continuo.ecommerce.Controller;


import com.continuo.ecommerce.DTO.ProductDTO;
import com.continuo.ecommerce.Services.ProductService;
import com.continuo.ecommerce.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/vendor/{email}")
    @PreAuthorize("hasRole('VENDOR') and authentication.name == #email")
    public ResponseEntity<Products> addProduct(@PathVariable("email") String email,
                                               @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(email, productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/vendor/{email}")
    @PreAuthorize("hasRole('VENDOR') and authentication.name == #email")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id,
                                                  @PathVariable String email,
                                                  @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(id, email, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('VENDOR') and authentication.name == #email")
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

    @GetMapping("/filter")
    public ResponseEntity<Page<Products>> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        try {
            Sort sortOrder = Sort.by(Arrays.stream(sort)
                    .map(s -> {
                        String[] parts = s.split(",");
                        if (parts.length != 2) {
                            // If no direction is specified, default to descending
                            return new Sort.Order(Sort.Direction.DESC, parts[0].trim());
                        }
                        String property = parts[0].trim();
                        String direction = parts[1].trim().toLowerCase();
                        
                        if (!direction.equals("asc") && !direction.equals("desc")) {
                            // If direction is invalid, default to descending
                            return new Sort.Order(Sort.Direction.DESC, property);
                        }
                        
                        return new Sort.Order(Sort.Direction.fromString(direction), property);
                    })
                    .toList());

            Pageable pageable = PageRequest.of(page, size, sortOrder);
            Page<Products> filtered = productService.filterProducts(category, minPrice, maxPrice, available, pageable);
            return ResponseEntity.ok(filtered);
        } catch (Exception e) {
            // If there's any error in sorting, return unsorted results
            Pageable pageable = PageRequest.of(page, size);
            Page<Products> filtered = productService.filterProducts(category, minPrice, maxPrice, available, pageable);
            return ResponseEntity.ok(filtered);
        }
    }

}
