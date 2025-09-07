package com.employeemanagement.employeemanagement.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.employeemanagement.employeemanagement.ConstantMessage.ErrorMsg;
import com.employeemanagement.employeemanagement.Entity.FileUploader;
import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.Repository.FileUploaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileDownloadImpl implements FileDownloadInterface {

    @Autowired
    private FileUploaderRepository fileUploaderRepository;
@Value("${file.upload-dir}")
 private String uploadFolder;
    @Override
    public ResponseEntity<?> downloadFile(Long id) {
        FileUploader fileUploader = fileUploaderRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorMsg.errorMessage, HttpStatus.NOT_FOUND);
        });
        String filePath = fileUploader.getPath();
        //how to check particular path exist or not in folder
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new AppException("Not found any file ", HttpStatus.NOT_FOUND, id);
        }
        //if files are found then check return
        try {
            Resource resource = new UrlResource(path.toUri());

           String contentType= Files.probeContentType(path);
           if(contentType==null){

               contentType="application/octet-stream";
           }
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.parseMediaType(contentType)).
                    header("Content-Disposition",
                    "attachment; filename=\"" + fileUploader.getFileName() + "\"").body(resource);
        } catch (Exception e) {
            throw new AppException("failed to download", HttpStatus.BAD_REQUEST);
        }
    }
}
