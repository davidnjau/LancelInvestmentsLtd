package com.centafrique.lancelinvestment.authentication.filter;


import com.centafrique.lancelinvestment.authentication.entity.Role;
import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import com.centafrique.lancelinvestment.authentication.repository.UserDetailsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AppUserDetailsService  implements UserDetailsService {
    private final UserDetailsRepository userRepository;

    public AppUserDetailsService( UserDetailsRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        UserDetails u = userRepository.findAllByEmailAddress( username );
        if(u == null) {
            throw new UsernameNotFoundException("User does not exist with email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                u.getEmailAddress(), u.getPassword(),
                u.getRolesCollection().stream().map( Role::getName )
                        .map( SimpleGrantedAuthority::new )
                        .collect( Collectors.toSet())
        );
    }
}
