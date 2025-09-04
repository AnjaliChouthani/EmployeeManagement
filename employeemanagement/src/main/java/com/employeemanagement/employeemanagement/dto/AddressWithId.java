package com.employeemanagement.employeemanagement.dto;


import lombok.Data;

@Data
public class AddressWithId {
    private Long id;
    private String street;
    private String city;
    private String pincode;
    private String state;
}
