package com.haratres.haratres.repository;

import com.haratres.haratres.model.ColorVariantProduct;
import com.haratres.haratres.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
