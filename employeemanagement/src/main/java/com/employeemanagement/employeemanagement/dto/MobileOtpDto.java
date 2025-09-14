package com.employeemanagement.employeemanagement.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MobileOtpDto {
     @Pattern(regexp = "^[6-9]\\d{9}")
    private String mobile;
    private String otp;
}
