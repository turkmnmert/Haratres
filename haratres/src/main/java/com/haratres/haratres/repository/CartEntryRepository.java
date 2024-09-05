package com.haratres.haratres.repository;

import com.haratres.haratres.model.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartEntryRepository extends JpaRepository<CartEntry , Long> {
}
