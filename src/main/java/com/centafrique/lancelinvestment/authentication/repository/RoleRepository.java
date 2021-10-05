package com.centafrique.lancelinvestment.authentication.repository;


import com.centafrique.lancelinvestment.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long > {

    Role findByName(String name);
    Boolean existsByName(String roleName);

}
