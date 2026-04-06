package com.technical.test.controller;

import com.technical.test.dto.request.ProductCreateRequest;
import com.technical.test.dto.request.ProductUpdateRequest;
import com.technical.test.dto.response.ProductDeleteResponse;
import com.technical.test.dto.response.ProductPageResponse;
import com.technical.test.service.ProductService;
import com.technical.test.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        ProductPageResponse products = productService.getAllProduct(
                page, size, sortBy, sortDir
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content", products.getContent());
        response.put("totalElements", products.getTotalElements());
        response.put("totalPages", products.getTotalPages());
        response.put("currentPage", products.getPage());
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        Object response = productService.updateProductById(id, request);
        return ResponseUtil.generateResponse("Success update product", HttpStatus.OK, response);
    }

    @PreAuthorize("hasRole('ADMIN, USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable Long id){
        Object response = productService.getProductById(id);
        return ResponseUtil.generateResponse("Success get product", HttpStatus.OK, response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        ProductDeleteResponse response = (ProductDeleteResponse) productService.deleteProductById(id);
        return ResponseUtil.generateResponse(response.getMessage(), HttpStatus.OK, new ArrayList<>());
    }

}



