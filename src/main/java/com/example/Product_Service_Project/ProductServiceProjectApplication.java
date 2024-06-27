package com.example.Product_Service_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.Product_Service_Project", "com.example.Product_Service_Project.config"})

public class ProductServiceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceProjectApplication.class, args);
	}

}
