package com.employeemanagement.employeemanagement.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.print.attribute.standard.JobKOctets;


@Getter
public class AppException extends RuntimeException{

    private String message;
   private HttpStatus status;
   private Object data;


   public AppException(String message,HttpStatus status){

       this(message,status,null);
   }

   public AppException(String message,HttpStatus status,Object object){
       this.status=status;
       this.message=message;
       this.data=object;
   }
}
