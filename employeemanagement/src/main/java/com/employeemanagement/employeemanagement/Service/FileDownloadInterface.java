package com.employeemanagement.employeemanagement.Service;

import org.springframework.http.ResponseEntity;

public interface FileDownloadInterface {

    public ResponseEntity<?> downloadFile(String filename);
}
