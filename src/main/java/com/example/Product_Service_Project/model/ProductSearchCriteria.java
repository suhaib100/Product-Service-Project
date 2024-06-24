package com.example.Product_Service_Project.model;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

@Data
public class ProductSearchCriteria {
    private Map<String, Object> search;
    private int page;
    private int size;



}
