package com.technical.test.controller;

import com.technical.test.dto.request.ProductCreateRequest;
import com.technical.test.entity.Product;
import com.technical.test.service.ProductService;
import com.technical.test.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Page<Product> products = productService.getAllProducts(
                page, size, sortBy, sortDir
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content", products.getContent());
        response.put("totalElements", products.getTotalElements());
        response.put("totalPages", products.getTotalPages());
        response.put("currentPage", products.getNumber());
        response.put("pageSize", products.getSize());

        return ResponseUtil.generateResponse(
                "Success get products",
                HttpStatus.OK,
                response
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductCreateRequest request){
        Object response = productService.createProduct(request);
        return ResponseUtil.generateResponse("Success create product", HttpStatus.CREATED, response);
    }

}



