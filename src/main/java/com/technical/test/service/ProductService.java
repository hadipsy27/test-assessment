package com.technical.test.service;

import com.technical.test.dto.request.ProductCreateRequest;
import com.technical.test.entity.Product;
import com.technical.test.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(
            value = "products",
            key = "'page:' + #page + ':size:' + #size + ':sort:' + #sortBy + ':' + #sortDir"
    )
    public Page<Product> getAllProducts(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        System.out.println("🔥 HIT DATABASE (NOT REDIS)");

        return productRepository.findAll(pageable);
    }


    public Object createProduct(ProductCreateRequest request){

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(BigDecimal.valueOf(request.getPrice()))
                .stock(request.getStock())
                .category(request.getCategory())
                .build();

        return productRepository.save(product);
    }

}
