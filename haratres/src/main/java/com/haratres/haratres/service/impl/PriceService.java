package com.haratres.haratres.service.impl;

import com.haratres.haratres.Utils.PriceUtil;
import com.haratres.haratres.dto.UpdatePriceDto;
import com.haratres.haratres.model.PriceRow;
import com.haratres.haratres.model.SizeVariantProduct;
import com.haratres.haratres.repository.PriceRowRepository;
import com.haratres.haratres.repository.SizeVariantProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private PriceRowRepository priceRowRepository;

    @Autowired
    private SizeVariantProductRepository sizeVariantProductRepository;

    @Transactional
    public void updatePrice(UpdatePriceDto updatePriceDto) {
        SizeVariantProduct sizeVariantProduct = sizeVariantProductRepository.findById(updatePriceDto.getSizeVariantProductId())
                .orElseThrow(() -> new RuntimeException("Size variant not found"));

        PriceRow priceRow = sizeVariantProduct.getPriceRow();

        if (priceRow == null) {
            priceRow = new PriceRow();
            priceRow.setSizeVariantProduct(sizeVariantProduct);
            sizeVariantProduct.setPriceRow(priceRow);
            priceRow.setSizeVariantProductId(sizeVariantProduct.getId());
        }

        priceRow.setPrice(updatePriceDto.getPrice());
        priceRow.setCurrency(updatePriceDto.getCurrency());
        priceRowRepository.save(priceRow);

        double convertedPrice = PriceUtil.convertCurrencyToTRY(updatePriceDto.getPrice(), updatePriceDto.getCurrency());
        sizeVariantProduct.setPrice(convertedPrice);

        sizeVariantProductRepository.save(sizeVariantProduct);
    }
}
