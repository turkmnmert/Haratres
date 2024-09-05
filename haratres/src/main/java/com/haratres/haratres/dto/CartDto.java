package com.haratres.haratres.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDto {

    public long id;
    public Long UserId;
    public List<CartEntryDto> cartEntries;
    public Double totalPrice;
    public String errorMessage;
}
