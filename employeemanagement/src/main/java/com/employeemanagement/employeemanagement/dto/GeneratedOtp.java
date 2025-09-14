package com.employeemanagement.employeemanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedOtp {

    private String mobileNumber;

    private String secretKey;

    private String apiKey;

}
