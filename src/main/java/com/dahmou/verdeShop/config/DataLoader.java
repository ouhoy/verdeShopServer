package com.dahmou.verdeShop.config;

import com.dahmou.verdeShop.model.Order;
import com.dahmou.verdeShop.model.Product;
import com.dahmou.verdeShop.repository.OrderCollectionRepository;
import com.dahmou.verdeShop.repository.ProductCollectionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductCollectionRepository productCollectionRepository;
    private final OrderCollectionRepository orderCollectionRepository;
    private final ObjectMapper objectMapper;
    public DataLoader(ProductCollectionRepository productCollectionRepository, OrderCollectionRepository orderCollectionRepository, ObjectMapper objectMapper) {
        this.productCollectionRepository = productCollectionRepository;
        this.orderCollectionRepository = orderCollectionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load products from JSON file and save to the database
        loadProductsFromJson();
    }

    private void loadProductsFromJson() {

        if (productCollectionRepository.getAllProducts().isEmpty()) {
            try {
                // Load products from the JSON file
                InputStream inputStream = new ClassPathResource("/data/products.json").getInputStream();
                List<Product> products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {
                });

                // Save each product to the database
                for (Product product : products) {
                    productCollectionRepository.createProduct(product);
                }

                System.out.println("Products loaded successfully.");

            } catch (IOException e) {
                System.err.println("Error loading products from JSON: " + e.getMessage());
            }
        }

        if (orderCollectionRepository.getAllOrders().isEmpty()) {
            try {
                // Load orders from the JSON file
                InputStream inputStream = new ClassPathResource("/data/orders.json").getInputStream();
                List<Order> orders = objectMapper.readValue(inputStream, new TypeReference<List<Order>>() {
                });

                // Save each order to the database
                for (Order order : orders) {
                    orderCollectionRepository.createOrder(order);
                }

                System.out.println("Orders loaded successfully.");

            } catch (IOException e) {
                System.err.println("Error loading orders from JSON: " + e.getMessage());
            }
        }
    }
}
