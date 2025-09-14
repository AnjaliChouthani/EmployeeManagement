package com.employeemanagement.employeemanagement.dto;

import lombok.Data;

@Data
public class OtpVerificationDto {

    private String mobileNumber;
    private String otp;
    private String secretKey;
    private String apiKey;
}
