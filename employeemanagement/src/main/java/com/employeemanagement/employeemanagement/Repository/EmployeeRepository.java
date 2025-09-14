package com.employeemanagement.employeemanagement.Repository;

import com.employeemanagement.employeemanagement.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);

    Employee findByUsernameAndPassword(String username, String password);

    Employee findByUsername(String username);
}
