package com.employeemanagement.employeemanagement.Controller;


import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.Service.UploadServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/upload")
public class FileUploadeController {


    @Autowired
    private UploadServiceInterface uploadServiceInterface;

    @PostMapping("/single-file-uploader")
    public ResponseEntity<String>uploadFile(@RequestParam("file") MultipartFile file,@RequestParam Long id)  {
        return uploadServiceInterface.uploadFile(file,id);
    }
}
