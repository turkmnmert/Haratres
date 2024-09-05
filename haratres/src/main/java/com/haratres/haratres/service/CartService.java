package com.haratres.haratres.service;


import com.haratres.haratres.dto.CartDto;

public interface CartService {

    CartDto getCurrentCart(Long userId);
    CartDto addProductToCart(Long userId, Long sizeVariantProduct , Integer quantity);
    CartDto updateCartEntry(Long cartEntryId , Integer quantity);
    CartDto cleanCart(Long userId);
}
