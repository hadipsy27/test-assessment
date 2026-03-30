package com.technical.test.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdateRequest {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;


}
