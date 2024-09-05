package com.haratres.haratres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "size_variant_product_id")
    private SizeVariantProduct sizeVariantProduct;

    private Integer quantity;
    private Double totalPrice;

    public void calculateTotalPrice() {
        this.totalPrice = this.sizeVariantProduct.getPrice() * this.quantity;
    }
}
