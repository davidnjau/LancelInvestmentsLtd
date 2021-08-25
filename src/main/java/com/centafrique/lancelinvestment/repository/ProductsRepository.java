package com.centafrique.lancelinvestment.repository;

import com.centafrique.lancelinvestment.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, String> {
}