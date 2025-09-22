package com.employeemanagement.employeemanagement.Service;


import com.employeemanagement.employeemanagement.ConstantMessage.ErrorMsg;
import com.employeemanagement.employeemanagement.Entity.Employee;
import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    @Autowired
   private EmployeeRepository employeeRepository;

    @Autowired
    private EmailSender emailOtpSender;

    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    @Value("${otp.expiry.minutes}")
    private int time;

    public void otpSender(String email) {

      Optional<Employee>employeeOptional =  employeeRepository.findByEmail(email);
      if(!employeeOptional.isPresent()){
          throw new AppException(ErrorMsg.notExistEmail, HttpStatus.NOT_FOUND,email);
      }
      //found email pr otp send kro redis k use krenge for storing otp
        String otp= String.valueOf(100000+ new Random().nextInt(0,900000));
         String to=employeeOptional.get().getEmail();
         String subject="otp for Registration";
         String body="this otp is valid only for 10 minutes, Kindly use it as soon as possible "+"\n"+
         "otp "+otp;
         redisTemplate.opsForValue().set(email,otp,time, TimeUnit.MINUTES);
      emailOtpSender.sendOtp(to,subject,body);
    }
    public ResponseEntity<String> otpChecker(String email, String otp) {
                  String tempOtp=redisTemplate.opsForValue().get(email);
                  if(tempOtp==null){
                       throw new AppException("Expire Otp ",HttpStatus.BAD_REQUEST);
                  }
                  if(!tempOtp.equals(otp)){
                      throw new AppException("Invalid Otp",HttpStatus.BAD_REQUEST);
                  }
        System.out.println(tempOtp+" "+ otp);
                   redisTemplate.delete(email);
                  return new ResponseEntity<>("valid otp ",HttpStatus.OK);
    }
}


