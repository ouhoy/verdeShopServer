package com.dahmou.verdeShop.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class ProductService {

    private static final String PRODUCT_API_URL = "https://verdeshopserver.onrender.com/v1/products/";

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 30000) // 2 minutes in milliseconds
    public void fetchProducts() {
        String result = restTemplate.getForObject(PRODUCT_API_URL, String.class);
        // Do something with the result
        System.out.println("Corn job is running every 30 seconds to fetch products from the API.");
    }
}