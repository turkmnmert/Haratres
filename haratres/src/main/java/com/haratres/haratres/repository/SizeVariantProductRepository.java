package com.haratres.haratres.repository;

import com.haratres.haratres.model.ColorVariantProduct;
import com.haratres.haratres.model.SizeVariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeVariantProductRepository extends JpaRepository<SizeVariantProduct, Long> {
    Optional<SizeVariantProduct> findByCode(String code);

}
