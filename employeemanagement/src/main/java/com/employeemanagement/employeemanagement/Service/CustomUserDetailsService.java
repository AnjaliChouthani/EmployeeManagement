package com.employeemanagement.employeemanagement.Service;

import com.employeemanagement.employeemanagement.Entity.Employee;
import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee=employeeRepository.findByUsername(username);
        if(employee==null){
            throw new UsernameNotFoundException("Username not found");
        }


        List<SimpleGrantedAuthority> authorityList=List.of(new SimpleGrantedAuthority("ROLE_"+employee.getRole()));
        return new User(employee.getUsername(),"{noop}"+employee.getPassword(), authorityList);
    }
}
