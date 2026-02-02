package com.technical.test.service;

import com.technical.test.entity.Product;
import com.technical.test.entity.User;
import com.technical.test.repository.ProductRepository;
import com.technical.test.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private UserService userService;


    public Product createProduct(Product product){
        return null;

    }
}
