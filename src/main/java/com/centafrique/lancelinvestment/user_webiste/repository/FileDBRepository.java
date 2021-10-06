package com.centafrique.lancelinvestment.user_webiste.repository;

import com.centafrique.lancelinvestment.user_webiste.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
