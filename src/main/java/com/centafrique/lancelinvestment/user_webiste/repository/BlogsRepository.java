package com.centafrique.lancelinvestment.user_webiste.repository;

import com.centafrique.lancelinvestment.user_webiste.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<Blogs, String> {

    Boolean existsByFeaturedImage(String fileName);
}