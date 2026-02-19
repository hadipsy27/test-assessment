package com.technical.test.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductCreateRequest {

    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;

}
