package com.employeemanagement.employeemanagement.Service;

import com.employeemanagement.employeemanagement.Entity.Employee;
import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CacheDetails {
    @Autowired
    private EmployeeRepository employeeRepository;

Logger logger= LoggerFactory.getLogger(CacheDetails.class);
    @Cacheable(value = "employeesdata")
    public List<Employee> getEmployee(){
         logger.info("cache run ");
        return employeeRepository.findAll();
    }
}
