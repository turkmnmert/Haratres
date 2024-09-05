package com.haratres.haratres.dto;

import com.haratres.haratres.model.Product;
import lombok.Data;
import java.util.List;

@Data
public class InboundProductRequestDto {

    public Integer idocNo; //Ürün döküman numarası
    public List<ColorVariantProductDto> products;

    private String code;
    private String name;
    private String color;
}
