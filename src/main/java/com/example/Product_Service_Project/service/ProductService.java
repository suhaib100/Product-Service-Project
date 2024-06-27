package com.example.Product_Service_Project.service;

import com.example.Product_Service_Project.model.Product;
import com.example.Product_Service_Project.model.ProductSearchCriteria;
import com.example.Product_Service_Project.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ProductService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(ProductSearchCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Map<String, Object> searchFields = criteria.getSearch();
        Query query = new Query();

        if (searchFields != null) {
            for (Map.Entry<String, Object> entry : searchFields.entrySet()) {
                if (entry.getValue() instanceof List) {
                    if (entry.getKey().equals("id")) {
                        query.addCriteria(Criteria.where(entry.getKey()).in((List<?>) entry.getValue()));
                    } else {
                        List<?> values = (List<?>) entry.getValue();
                        query.addCriteria(Criteria.where(entry.getKey()).in(values.stream()
                                .map(value -> Pattern.compile(Pattern.quote(value.toString()), Pattern.CASE_INSENSITIVE))
                                .toArray(Pattern[]::new)));
                    }
                } else if (entry.getKey().equals("name")) {
                    query.addCriteria(Criteria.where(entry.getKey()).regex(Pattern.compile(Pattern.quote(entry.getValue().toString()), Pattern.CASE_INSENSITIVE)));
                } else if (entry.getKey().equals("price")) {
                    query.addCriteria(Criteria.where(entry.getKey()).regex(Pattern.compile(Pattern.quote(entry.getValue().toString()), Pattern.CASE_INSENSITIVE)));
                } else {
                    query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
                }
            }
        }

        long total = mongoTemplate.count(query, Product.class);
        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);

        return new PageImpl<>(products, pageable, total);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }


    public Product updateProduct(String id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
