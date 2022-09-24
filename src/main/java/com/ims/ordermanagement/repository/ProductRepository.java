package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findBySlug(String productSlug);

    Optional<List<Product>> findByCategoryIn(List<String> rightCategory);
}