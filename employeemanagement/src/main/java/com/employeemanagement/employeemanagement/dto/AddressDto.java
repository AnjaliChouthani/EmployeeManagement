package com.employeemanagement.employeemanagement.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String street;
    private String city;
    private String pincode;
    private String state;
}
