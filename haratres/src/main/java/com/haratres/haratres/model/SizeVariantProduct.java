package com.haratres.haratres.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SizeVariantProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private Integer stock;
    private Double price;
    private String code;

    @ManyToOne
    @JoinColumn(name = "color_variant_product_id")
    private ColorVariantProduct colorVariantProduct;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_row_id")
    private PriceRow priceRow;

    public void setPriceRow(PriceRow priceRow) {
        this.priceRow = priceRow;
        if (priceRow != null) {
            priceRow.setSizeVariantProduct(this);
        }
    }
}
