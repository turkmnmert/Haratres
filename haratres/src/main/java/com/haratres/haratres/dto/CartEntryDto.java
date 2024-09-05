package com.haratres.haratres.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartEntryDto {

    public Long id;
    public Long sizeVariantProductId;
    public Integer quantity;
    public Double totalPrice;
}
