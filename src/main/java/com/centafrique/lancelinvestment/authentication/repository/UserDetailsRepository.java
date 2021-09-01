package com.centafrique.lancelinvestment.authentication.repository;

import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String > {

    UserDetails findAllByEmailAddress(String email);
    Boolean existsByEmailAddress(String emailAddress);
    Boolean existsByUsername(String userName);
    UserDetails findAllByUserId(String userId);
}
