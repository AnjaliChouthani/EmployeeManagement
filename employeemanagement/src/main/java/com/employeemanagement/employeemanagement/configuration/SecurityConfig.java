package com.employeemanagement.employeemanagement.configuration;


import com.employeemanagement.employeemanagement.helper.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        //public endpoints
                        .requestMatchers("/employeeCon/addEmployee").permitAll()
                        .requestMatchers("/generateToken").permitAll()
                        .requestMatchers("/otpController/**").permitAll()
                        .requestMatchers("/calling_third_party/**").permitAll()
                        .requestMatchers("/verificationController/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()
                        //role based endpoints
                        .requestMatchers("/employeeCon/getAllEmployee").hasAnyRole("Admin","Manager")
                        .requestMatchers("/employeeCon/getById/**").hasAnyRole("Admin","Manager")
                        .requestMatchers(HttpMethod.DELETE,"/employeeCon/deleteById/**").hasRole("Admin")
                        .requestMatchers("/employeeCon/updateById/**").hasAnyRole("Admin","Manager")
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
             return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
