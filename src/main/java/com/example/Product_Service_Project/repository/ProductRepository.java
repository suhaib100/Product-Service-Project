package com.example.Product_Service_Project.repository;

import com.example.Product_Service_Project.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{ 'id': { '$in': ?0 }, 'name': { '$in': ?1 }, 'price': { '$gte': ?2, '$lte': ?3 } }")
    List<Product> findByIdInAndNameInAndPriceBetween(List<String> ids, List<String> names, double minPrice, double maxPrice);

    @Query("{ 'id': { '$in': ?0 }, 'name': { '$in': ?1 } }")
    List<Product> findByIdInAndNameIn(List<String> ids, List<String> names);

    @Query("{ 'name': { '$in': ?0 }, 'price': { '$gte': ?1, '$lte': ?2 } }")
    List<Product> findByNameInAndPriceBetween(List<String> names, double minPrice, double maxPrice);

    @Query("{ 'name': { '$in': ?0 } }")
    List<Product> findByNameIn(List<String> names);}
