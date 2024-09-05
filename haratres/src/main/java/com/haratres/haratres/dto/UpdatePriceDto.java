package com.haratres.haratres.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePriceDto {

     public Long sizeVariantProductId;
     public double price;
     public String currency;
}
