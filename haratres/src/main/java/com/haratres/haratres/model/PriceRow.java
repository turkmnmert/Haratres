package com.haratres.haratres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class PriceRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    private String currency;

    @Column(name = "size-variant_product_id")
    private Long sizeVariantProductId;

    @OneToOne(mappedBy = "priceRow")
    private SizeVariantProduct sizeVariantProduct;
}
