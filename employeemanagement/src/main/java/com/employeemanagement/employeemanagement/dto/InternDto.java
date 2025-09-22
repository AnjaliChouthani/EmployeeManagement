package com.employeemanagement.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternDto {

        private int id;
        private String name;
        private String email;
        private String role;
        private String operation;

}
