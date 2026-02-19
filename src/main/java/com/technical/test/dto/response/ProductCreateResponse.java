package com.technical.test.dto.response;

import com.technical.test.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreateResponse {

    Product product;

}
