package com.haratres.haratres.controller;

import com.haratres.haratres.dto.UpdatePriceDto;
import com.haratres.haratres.service.impl.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PutMapping("/update")
    public ResponseEntity<?> updatePrice(@RequestBody UpdatePriceDto updatePriceDto) {
        priceService.updatePrice(updatePriceDto);
        return ResponseEntity.ok("Ürün fiyatı başarıyla güncellendi.");
    }
}
