package com.haratres.haratres.service;

import com.haratres.haratres.dto.InboundProductRequestDto;
import com.haratres.haratres.dto.ProductResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {

    void createProduct(InboundProductRequestDto productRequest);

    ProductResponseDto getProductByCode(String productCode);
}
