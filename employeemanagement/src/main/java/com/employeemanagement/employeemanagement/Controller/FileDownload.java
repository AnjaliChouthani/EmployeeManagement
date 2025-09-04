package com.employeemanagement.employeemanagement.Controller;


import com.employeemanagement.employeemanagement.Service.FileDownloadInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download-file")
public class FileDownload {


    @Autowired
    private FileDownloadInterface downloadInterface;

    public ResponseEntity<?>downloadFile(@RequestParam String filename){
        return downloadInterface.downloadFile(filename);
    }


}
