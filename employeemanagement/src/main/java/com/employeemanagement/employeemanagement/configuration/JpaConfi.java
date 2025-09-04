package com.employeemanagement.employeemanagement.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class JpaConfi {
    @Bean
    public AuditorAware<String>auditorAware(){
        return () -> Optional.of("SYSTEM");
    }
}
