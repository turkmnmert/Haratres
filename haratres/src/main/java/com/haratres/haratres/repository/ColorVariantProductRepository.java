package com.haratres.haratres.repository;

import com.haratres.haratres.model.ColorVariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorVariantProductRepository extends JpaRepository<ColorVariantProduct, Long> {
    Optional<ColorVariantProduct> findByCode(String code);
}
