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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

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
        File f=new File(uploadFolder);
        if(!f.exists()){
            f.mkdirs();
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
        uploader.setStoredFileName(System.currentTimeMillis()+"-"+employee.getId()+"-"+employee.getName()+"-"+file.getOriginalFilename());

        if (file.getContentType() != null) {
            uploader.setFileType(file.getContentType());
        }
        else{
            uploader.setFileType("unknown");
        }
        fileUploaderRepository.save(uploader);
        return new ResponseEntity<>("saved",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> uploadMultipleFile(List<MultipartFile> file, Long id) {

        //check employee exist or not
       Employee employee=   employeeRepository.findById(id).orElseThrow(()->{return new AppException(ErrorMsg.errorMessage,HttpStatus.NOT_FOUND);});

       //file is empty or not
        for(MultipartFile file1:file){
             if(file1.isEmpty()){
                 throw new AppException("file is empty",HttpStatus.BAD_REQUEST);
             }
            //use logger
        }
            //check folder exist or not

          File f=new File(uploadFolder);
          if(!f.exists()){
               f.mkdirs();
          }
          //store k liye path


        String curr=System.getProperty("user.dir")+File.separator+uploadFolder;

             List<FileUploader>fileList= file.stream().map((list)-> {
                  try {
                      String path=curr+File.separator+list.getOriginalFilename();
                      //transfer the list file to specific path
                      list.transferTo(new File(path));
                      FileUploader fileUploader=new FileUploader();
                      fileUploader.setFileName(list.getOriginalFilename());
                      fileUploader.setEmployee(employee);
                      fileUploader.setPath(path);
                      fileUploader.setStoredFileName(System.currentTimeMillis()+"-"+employee.getId()+"-"+employee.getName()+list.getOriginalFilename());
                      if(list.getContentType()!=null){
                          fileUploader.setFileType(list.getContentType());
                      }
                      else{
                          fileUploader.setFileType("unknown");
                      }
                      return fileUploader;
                  } catch (IOException e) {
                      throw new AppException("failed to save file",HttpStatus.BAD_REQUEST);
                  }

             }).toList();
             fileUploaderRepository.saveAll(fileList);
       //storing the data into db
        return new ResponseEntity<>("saved all file ", HttpStatus.OK);
    }
}

