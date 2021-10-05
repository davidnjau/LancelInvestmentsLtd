package com.centafrique.lancelinvestment.user_webiste.repository;

import com.centafrique.lancelinvestment.user_webiste.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, String> {
}