package com.haratres.haratres.controller;

import com.haratres.haratres.dto.InboundProductRequestDto;
import com.haratres.haratres.dto.ProductResponseDto;
import com.haratres.haratres.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody InboundProductRequestDto productRequest){
        productService.createProduct(productRequest);
        return new ResponseEntity<>("Product created successfully" , HttpStatus.CREATED);
    }

    @GetMapping("/{productCode}")
    public ProductResponseDto getProductByCode(@PathVariable String productCode){
        return productService.getProductByCode(productCode);
    }
}
