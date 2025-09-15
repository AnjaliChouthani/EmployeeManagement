package com.employeemanagement.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")

public class EmployeemanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagementApplication.class, args);
	}
}
