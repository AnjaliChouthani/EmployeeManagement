package com.employeemanagement.employeemanagement.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileDto {
    @Pattern(regexp ="^[6-9]\\d{9}",message = "invalid mobile number ")
    private String mobileNumber;
}

