package com.haratres.haratres.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ColorVariantProductDto {
    public String code;
    private String name;
    public String description;
    public String color;
    public String gender;
    public List<SizeVariantProductDto> variants;

}
