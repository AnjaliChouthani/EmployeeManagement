package com.employeemanagement.employeemanagement.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class FileDownloadImpl implements FileDownloadInterface {

@Value("${file.upload-dir}")
 private String uploadFolder;
    @Override
    public ResponseEntity<?> downloadFile(String filename) {

        try{
            String filePath=System.getProperty("user.dir")+ File.separator+uploadFolder+File.separator+filename;
            File file=new File(filePath);
            if(!file.exists()){
                //code
            }
            FileInputStream fileInputStream=new FileInputStream(file);


            //
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
