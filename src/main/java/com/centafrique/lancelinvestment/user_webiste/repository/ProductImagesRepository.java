package com.centafrique.lancelinvestment.user_webiste.repository;

import com.centafrique.lancelinvestment.user_webiste.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, String> {

    List<ProductImages> findProductImagesByProductId(String productId);

}