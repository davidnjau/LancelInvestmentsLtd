package com.centafrique.lancelinvestment;

import com.centafrique.lancelinvestment.authentication.entity.Role;
import com.centafrique.lancelinvestment.authentication.service_class.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor

public class LancelInvestmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LancelInvestmentApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(UserDetailsServiceImpl userDetailsService){

        return args -> {

            userDetailsService.saveRoles(new Role( "ROLE_ADMIN"));
            userDetailsService.saveRoles(new Role( "ROLE_USER"));

        };

    }
}
