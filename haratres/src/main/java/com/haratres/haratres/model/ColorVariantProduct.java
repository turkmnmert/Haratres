package com.haratres.haratres.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "color_variant_product")
public class ColorVariantProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String description;
    private String color;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "colorVariantProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizeVariantProduct> sizeVariants;
}
