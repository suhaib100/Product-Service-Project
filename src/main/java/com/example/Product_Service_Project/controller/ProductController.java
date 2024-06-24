package com.example.Product_Service_Project.controller;
import com.example.Product_Service_Project.model.Product;
import com.example.Product_Service_Project.model.ProductSearchCriteria;
import com.example.Product_Service_Project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/search")
    public Page<Product> searchProducts(@RequestBody ProductSearchCriteria criteria) {
        return productService.searchProducts(criteria);
    }


    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
}
