package com.employeemanagement.employeemanagement.Exception;


import com.employeemanagement.employeemanagement.dto.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseApi>handleAppException(AppException exception){
        ResponseApi responseApi=new ResponseApi();
        responseApi.setMessage(exception.getMessage());
        responseApi.setError(true);
        HashMap<String,Object>meta=new HashMap<>();
        meta.put("data",exception.getData());
        responseApi.setMeta(meta);
        return new ResponseEntity<>(responseApi,exception.getStatus());
    }
}
