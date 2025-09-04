package com.employeemanagement.employeemanagement.dto;


import lombok.Data;

import java.util.HashMap;
import java.util.Objects;

@Data
public class ResponseApi {

    private String message;
    private boolean isError;
    private HashMap<String, Object>meta=new HashMap<>();
}
