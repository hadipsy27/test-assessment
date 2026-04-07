package com.technical.test.service;

import com.technical.test.dto.request.ProductCreateRequest;
import com.technical.test.dto.request.ProductUpdateRequest;
import com.technical.test.dto.response.ProductDeleteResponse;
import com.technical.test.dto.response.ProductPageResponse;
import com.technical.test.dto.response.ProductResponse;
import com.technical.test.entity.Product;
import com.technical.test.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(
            value = "products",
            key = "T(String).format('page:%d:size:%d:sort:%s:%s', #page, #size, #sortBy, #sortDir)"
    )
    public ProductPageResponse getAllProduct(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        System.out.println("🔥 HIT DATABASE (NOT REDIS)");

        Page<Product> result = productRepository.findAll(pageable);

        List<ProductResponse> content = result.getContent()
                .stream()
                .map(p -> ProductResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .stock(p.getStock())
                        .category(p.getCategory())
                        .build())
                .toList();

        return ProductPageResponse.builder()
                .content(content)
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }


    @CacheEvict(value = "products", allEntries = true)
    public Object createProduct(ProductCreateRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(BigDecimal.valueOf(request.getPrice()))
                .stock(request.getStock())
                .category(request.getCategory())
                .build();

        return productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Object updateProductById(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(BigDecimal.valueOf(request.getPrice()));
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Object getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @CacheEvict(value = "products", allEntries = true)
    public Object deleteProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
        return new ProductDeleteResponse("Product deleted successfully");
    }

    @CacheEvict(value = "products", allEntries = true)
    public Object findProductsByName(String name){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .category(product.getCategory())
                        .build())
                .toList();
    }

}
