package com.employeemanagement.employeemanagement.Service;

import com.employeemanagement.employeemanagement.dto.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface UploadServiceInterface{

    ResponseEntity<String> uploadFile(MultipartFile file, Long id);

    ResponseEntity<String> uploadMultipleFile(List<MultipartFile> file, Long id);
}
