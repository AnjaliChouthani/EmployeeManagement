package com.employeemanagement.employeemanagement.Service;


import com.employeemanagement.employeemanagement.ConstantMessage.ErrorMsg;
import com.employeemanagement.employeemanagement.Entity.Employee;
import com.employeemanagement.employeemanagement.Entity.FileUploader;
import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import com.employeemanagement.employeemanagement.Repository.FileUploaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class UploadeServiceImple implements UploadServiceInterface{



    @Value("${file.upload-dir}")
    private String uploadFolder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired

    private FileUploaderRepository fileUploaderRepository;

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file, Long id) {

        Employee employee=employeeRepository.findById(id).orElseThrow(()->{
            throw new AppException(ErrorMsg.errorMessage,HttpStatus.NOT_FOUND,id);
        });

        if(file.isEmpty()){
            throw new AppException("file is empty",HttpStatus.BAD_REQUEST);
        }

        //folder exist or not
        File file1=new File(uploadFolder);
        if(!file1.exists()){
            file1.mkdirs();
        }
        //save to storage
        String currPath=System.getProperty("user.dir");
//        String filePath=uploadFolder+File.separator+file.getOriginalFilename();
        String filePath=currPath+File.separator+uploadFolder+File.separator+file.getOriginalFilename();
        try{
            file.transferTo(new File(filePath));
        }
        catch (IOException e){
            e.printStackTrace();
            throw new AppException("failed to save",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //
        FileUploader uploader=new FileUploader();
        uploader.setEmployee(employee);
        uploader.setPath(filePath);
        uploader.setFileName(file.getOriginalFilename());

        if (file.getContentType() != null) {
            uploader.setFileType(file.getContentType());
        }
        else{
            uploader.setFileType("unknown");
        }
        fileUploaderRepository.save(uploader);
        return new ResponseEntity<>("saved",HttpStatus.OK);
    }
}
