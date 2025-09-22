package com.employeemanagement.employeemanagement.helper;

import com.employeemanagement.employeemanagement.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtilClass jwtUtilClass;
    @Autowired
    private CustomUserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/employeeCon/addEmployee") ||
                path.startsWith("/generateToken") ||
                path.startsWith("/otpController") ||
                path.startsWith("/calling_third_party") ||
                path.startsWith("/verificationController") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/webjars")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeaader = request.getHeader("Authorization");
        if (authHeaader != null && authHeaader.startsWith("Bearer ")) {
            String token = authHeaader.substring(7);
                String username = jwtUtilClass.extractUsernameByToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtilClass.validateToken(token, userDetails)) {
                    String role= jwtUtilClass.extractRole(token);
                    List<GrantedAuthority>authorityList=List.of(new SimpleGrantedAuthority(role));
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
