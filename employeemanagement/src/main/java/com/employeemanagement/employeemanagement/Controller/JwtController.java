package com.employeemanagement.employeemanagement.Controller;

import com.employeemanagement.employeemanagement.Service.CustomUserDetailsService;
import com.employeemanagement.employeemanagement.helper.JwtUtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class JwtController {

    @Autowired
    private JwtUtilClass jwtUtilClass;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/generateToken")
    public String generateToken(@RequestParam String username,@RequestParam String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return jwtUtilClass.generateToken(userDetails);
    }

}
