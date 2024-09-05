package com.haratres.haratres.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //ekrana fazladan dönen hatayı gizlemek için
public class ProductResponseDto {

    public String errorMessage;
    public ProductDto product;
}
