package com.haratres.haratres.service;

import com.haratres.haratres.dto.InboundProductRequestDto;
import com.haratres.haratres.dto.ProductResponseDto;


public interface ProductService {

    void createProduct(InboundProductRequestDto productRequest);

    ProductResponseDto getProductByCode(String productCode);
}
