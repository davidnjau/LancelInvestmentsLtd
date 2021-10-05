package com.centafrique.lancelinvestment.authentication.service_class.service;


import com.centafrique.lancelinvestment.authentication.entity.UserDetails;

import java.util.List;

public interface UserDetailsService {

    UserDetails addUserDetails(UserDetails userDetails);
    List<UserDetails> getAllUsers();
//    List<UserDetails> getRegNonUsers(Boolean verified);
    UserDetails getUserDetailsByEmailAddress(String emailAddress);
//    boolean isVerified(String userId);
    boolean isEmailAddress(String emailAddress);
    boolean isUserName(String userName);

}
