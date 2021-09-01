package com.centafrique.lancelinvestment.user_webiste.repository;

import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSizesRepository extends JpaRepository<ProductSizes, String> {

    Optional<ProductSizes> findAllByProductIdAndSizeAmount(String productId, double productSize);

}